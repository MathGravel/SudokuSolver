/*
 * JSudokuGrid.java
 *
 * Created on 28. Oktober 2007, 00:19
 *
 *  Copyright (C) 28. Oktober 2007  <reiner>
 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */


package jsudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Font;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;    
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

    
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import tutorial.student.Student;
    
/**
 *
 * @author reiner
 */

public final class JSudokuGrid extends JComponent implements Printable
{
    private JSudokuTable m_table;
    private int m_xOffset = 4;
    private int m_yOffset = 4;
    private int m_headerHeight = 0;
    private int m_footerHeight = 0;
    private JTextField m_textField;
    private Font m_font;
    private int m_lastRow = -1;
    private int m_lastCol = -1;
    private boolean [][] m_initMask;
    private boolean[][] m_invalidMask;
    private Dimension m_oldDim = new Dimension();
    private boolean m_bPrintFooter = true;
    private boolean m_bPrintHeader = true;
    private int m_lastPage = -1;
    private boolean m_bPrintSolved = false;
    private Student student = null;
    
    public void setPrintSolved(boolean bPrintSolved)
    {
	m_bPrintSolved = bPrintSolved;
    }

    public boolean getPrintSolved()
    {
	return m_bPrintSolved;
    }
    
    public void setPrintFooter(boolean bPrintFooter)
    {
	m_bPrintFooter = bPrintFooter;
    }

    public boolean getPrintFooter()
    {
	return m_bPrintFooter;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    /** Creates a new instance of JSudokuGrid */
    public JSudokuGrid(JSudokuTable table)
    {
	super();
	setOpaque(true);
	m_table = table;
	m_initMask = new boolean[9][9];
	m_invalidMask = new boolean[9][9];
	initMask(false);
	setLayout(new SpringLayout());
	final JSudokuGrid grid = this;
	addMouseListener(new MouseAdapter()
	{
	    public void mouseClicked(MouseEvent ev)
	    {
		int w = m_oldDim.width - m_xOffset*2;
		int h = m_oldDim.height - m_yOffset*2;
		Point pt = ev.getPoint();
		if (pt.x > m_xOffset && pt.x < m_xOffset + w && pt.y > m_yOffset + m_headerHeight && pt.y < h + m_yOffset + m_headerHeight)
		{
		    int col = ((pt.x - m_xOffset)*9)/w;
		    int row = ((pt.y - m_yOffset - m_headerHeight)*9)/h;
		    if (ev.getButton() == MouseEvent.BUTTON1 && !m_initMask[row][col])
		    {
			setField(false);
			m_lastCol = col;
			m_lastRow = row;
			
			m_textField = new JTextField(m_table.m_data[row][col].getNum()!= 0 ? m_table.m_data[row][col].toString() : "");
			m_textField.setForeground(Main.m_settings.getDisplayUserTextColor());
			m_textField.addKeyListener(new KeyAdapter()
			{
			    public void keyTyped(KeyEvent ev)
			    {
				char ch = ev.getKeyChar();
				if (ch == KeyEvent.VK_ESCAPE)
				    grid.removeTextField(true);
				if (ch == KeyEvent.VK_ENTER){
				    setField(true);
                                }
			    }
			});
			addTextField();
			m_textField.requestFocusInWindow();
		    }
                    else if (ev.getButton() == MouseEvent.BUTTON3 && !m_initMask[row][col]) {
                        JPoss win = new JPoss(m_table.m_data[row][col]);
                        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        win.setVisible(true);
                        setField(true);
                        repaint();
                        revalidate();
                    }
		    else setField(true);
		}
	    }
	});
	addComponentListener(new ComponentAdapter()
	{
	    public void componentResized(ComponentEvent ev)
	    {
		if (m_textField != null)
		    remove(m_textField);
		addTextField();
	    }
	});
    }
    
    public boolean[][] getInvalidMask()
    {
	return m_invalidMask;
    }

    public boolean[][] getInitMask()
    {
	return m_initMask;
    }
    
    public void reset()
    {
	int row, col;
	for (row=0; row<9; row++)
	{
	    for (col=0; col<9; col++)
	    {
		if (!m_initMask[row][col])
		    m_table.m_data[row][col].setNum(0);
	    }
	}
	initMask(false);
    }
    
    public void initMask(boolean bRepaint)
    {
	int row, col;
	for (row=0; row<9; row++)
	{
	    for (col=0; col<9; col++)
	    {
                System.out.print(row + " " + col + " " + m_table.m_data[row][col].getNum() );
		m_initMask[row][col] = m_table.m_data[row][col].getNum() != 0;
		m_invalidMask[row][col] = false;
	    }
	}
	if (bRepaint)
	    repaint();
    }
    
    protected void addTextField()
    {
	if (m_textField != null && m_lastCol >= 0 && m_lastRow >= 0)
	{
	    m_textField.setFont(m_font);
	    int yOffset = m_yOffset + m_headerHeight;
	    int w = m_oldDim.width - m_xOffset*2;
	    int h = m_oldDim.height - m_yOffset*2;
	    int x = m_xOffset+(m_lastCol*w)/9 + 4;
	    int y = yOffset+(m_lastRow*h)/9 + 4;
	    int width = w/9 - 8;
	    int height = h/9 - 8;
	    add(m_textField, new SpringLayout.Constraints(Spring.constant(x), Spring.constant(y), Spring.constant(width), Spring.constant(height)));
	    validate();
	    repaint();
	}
    }
    protected void setField(boolean bValidateAndPaint)
    {
	if (m_textField != null && m_lastRow >= 0 && m_lastCol >= 0)
	{
	    String str = m_textField.getText();
	    if (str.isEmpty())
		m_table.m_data[m_lastRow][m_lastCol].setNum(0);
	    else
	    {
		try
		{
		    int val = Integer.parseInt(str);
		    if (str.length() == 1 && val >= 1 && val <=9){
			m_table.m_data[m_lastRow][m_lastCol].setNum(val);
                        checkStrategie();
                    }
		}
		catch (NumberFormatException ex)
		{}
	    }
	    removeTextField(bValidateAndPaint);
	}
    }
    
    protected void removeTextField(boolean bValidateAndPaint)
    {
	if (m_textField != null)
	{
	    remove(m_textField);
	    m_textField = null;
	    m_lastRow = -1;
	    m_lastCol = -1;
	    if (bValidateAndPaint)
	    {
		validate();
		repaint();
	    }
	}
    }
    
    public int print(Graphics pg, PageFormat pageFormat, int pageIndex)
    {
	boolean bFlag = false;
	if (pageIndex == 0)
	{
	    bFlag = true;
	}
	else if (pageIndex == 1)
	{
	    if (m_bPrintSolved)
	    {
		if (m_lastPage == 0)
		{
		    if (m_table.doSolve())
			setPrintFooter(false);
		    else 
		    {
			JOptionPane.showMessageDialog(Main.m_mainFrame, Main.getString("msg_unabletosolve"), Main.getMessageBoxCaption(), JOptionPane.WARNING_MESSAGE, null);
			return NO_SUCH_PAGE;
		    }
		}
		bFlag = true;
	    }
	}
	if (bFlag)
	{
	    pg.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
	    RepaintManager currentManager = RepaintManager.currentManager(this);
	    currentManager.setDoubleBufferingEnabled(false);
	    Graphics2D g2d = (Graphics2D)pg;
	    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    doPaint(pg, new Dimension((int)pageFormat.getImageableWidth(), (int)pageFormat.getImageableHeight()), true);
	    currentManager.setDoubleBufferingEnabled(true);
	    System.gc();
	    m_lastPage = pageIndex;
	    return PAGE_EXISTS;
	}
	else return NO_SUCH_PAGE;
    }

    public void paintComponent(Graphics g)
    {
	Graphics gd = g.create();
	doPaint(gd, getSize(), false);
    }
    
    public void doPaint(Graphics g, Dimension dim, boolean bPrint)
    {
	Graphics2D g2d = (Graphics2D)g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setColor(bPrint ? Main.m_settings.getPrintBackColor() : Main.m_settings.getDisplayBackColor());
	g2d.fillRect(0, 0, dim.width, dim.height);
	TextLayout layout;
	Rectangle2D rect;
        Color red = new Color(150,0,0);
	if (m_bPrintFooter)
	{
	    g2d.setFont(getFont().deriveFont(Font.BOLD, 16));
	    layout = new TextLayout("X", g2d.getFont(), g2d.getFontRenderContext());
	    m_footerHeight = (int)(1.5*layout.getBounds().getHeight() + 2*m_yOffset);
	}
	int w = dim.width - m_xOffset*2;
	if (m_bPrintHeader)
	{
	    g2d.setColor(bPrint ? Main.m_settings.getPrintInitTextColor() : Main.m_settings.getDisplayInitTextColor());
	    g2d.setFont(getFont().deriveFont(Font.BOLD, 16));
	    String str = String.format(Main.m_settings.getResBundleString("display_header"), m_table.getHash());
	    layout = new TextLayout(str, g2d.getFont(), g2d.getFontRenderContext());
	    rect = layout.getBounds();
	    int x = m_xOffset + w/2 - (int)(rect.getWidth()/2.0);
	    int y = m_yOffset + (int)(1.5*rect.getHeight());
	    g2d.drawString(str, x, y);
	    m_headerHeight = (int)(1.5*rect.getHeight()) + 2*m_yOffset;
	}
	else m_headerHeight = 0;
	dim.height -= (m_headerHeight + m_footerHeight);
	
	if (bPrint)
	    dim.width = dim.height = Math.min(dim.width, dim.height);
	
	w = dim.width - m_xOffset*2;
	int h = dim.height - m_yOffset*2;
	int yOffset = m_yOffset + m_headerHeight;
	g2d.setColor(bPrint ? Main.m_settings.getPrintGridLineColor() : Main.m_settings.getDisplayGridLineColor());
	for (int i=0; i<10; i++)
	{
	    g2d.setStroke(new BasicStroke(i%3 != 0 ? 1.0f : 2.0f));
	    g2d.drawLine(m_xOffset, yOffset+(i*h)/9, m_xOffset + w, yOffset+(i*h)/9);
	    
	}
	for (int i=0; i<10; i++)
	{
	    g2d.setStroke(new BasicStroke(i%3 != 0 ? 1.0f : 2.0f));
	    g2d.drawLine(m_xOffset+(i*w)/9, yOffset, m_xOffset+(i*w)/9, yOffset+h);
	}
	
	int row, col;
	Font font = m_font;
	if (m_font == null || !m_oldDim.equals(dim) || bPrint)
	{
	    g2d.setFont(getFont().deriveFont(Font.BOLD, 32));
	    layout = new TextLayout("8", g2d.getFont(), g2d.getFontRenderContext());
	    rect = layout.getBounds();
	    font = getFont().deriveFont(Font.BOLD, (int)(h/(9.0*rect.getHeight())*32.0*0.5));
	}
	g2d.setFont(font);
	layout = new TextLayout("8", g2d.getFont(), g2d.getFontRenderContext());
	rect = layout.getBounds();
	
	for (row=0; row<9; row++)
	{
	    for (col=0; col<9; col++)
	    {
		if (m_table.m_data[row][col].getNum() != 0 )
		{
		    String str = m_table.m_data[row][col].toString();
		    int x = m_xOffset+(col*w)/9 + w/18 - (int)(rect.getWidth()/2);
		    int y = yOffset+(row*h)/9 + h/18 + (int)(rect.getHeight()/2);
		    g2d.setColor(m_initMask[row][col] ? (bPrint ? Main.m_settings.getPrintInitTextColor() : Main.m_settings.getDisplayInitTextColor()) : (m_invalidMask[row][col] ? (bPrint ? Main.m_settings.getPrintInvalidTextColor() : Main.m_settings.getDisplayInvalidTextColor()) : (bPrint ? Main.m_settings.getPrintUserTextColor() : Main.m_settings.getDisplayUserTextColor())));
		    g2d.drawString(str, x, y);
		}
                else if (m_table.m_data[row][col].toString() != "") {
                    String str = m_table.m_data[row][col].toString();
		    int x = m_xOffset+(col*w)/9 ;
		    int y = yOffset+(row*h)/9  + (int)(rect.getHeight()/2);
                    Font f = font.deriveFont((float)(font.getSize()/2));
                    g2d.setFont(f);
                    g2d.setColor(red);
                    y -= g2d.getFontMetrics().getHeight();
                    for (String line : str.split("\n")) {
                        g2d.drawString(line,x,y += g2d.getFontMetrics().getHeight());
                    }
		    //g2d.drawString(str, x, y);
                    g2d.setFont(font);
                } else if (m_table.m_data[row][col].getNum() == 0 && m_table.m_data[row][col].isFond()){
                    System.out.println("modifier fond");
                    g2d.setColor(red);
                    int x = m_xOffset+(col*w)/9;
		    int y = yOffset+(row*h)/9;
                    g2d.drawRect(x, y, w/9, h/9);
                    g2d.setFont(font);
                }
	    }
	}
	if (m_bPrintFooter)
	{
	    g2d.setColor(bPrint ? Main.m_settings.getPrintInitTextColor() : Main.m_settings.getDisplayInitTextColor());
	    g2d.setFont(getFont().deriveFont(Font.BOLD, 16));
	    String str;
	    if (bPrint)
		str = String.format(Main.m_settings.getResBundleString("print_footer"), m_table.geNumberOfFreeFields(), getNumberOfInitFields());
	    else
		str = String.format(Main.m_settings.getResBundleString("display_footer"), m_table.geNumberOfFreeFields(), getNumberOfInitFields());
	    layout = new TextLayout(str, g2d.getFont(), g2d.getFontRenderContext());
	    rect = layout.getBounds();
	    int x = m_xOffset + w/2 - (int)(rect.getWidth()/2.0);
	    int y = yOffset + h + (int)(1.5*rect.getHeight());
	    g2d.drawString(str, x, y);
	}
	if (!bPrint)
	{
	    m_oldDim = dim;
	    m_font = font;
	}
    }
    
    private int getNumberOfInitFields()
    {
	int count = 0;
	int row, col;
	for(row=0; row<9; row++)
	{
	    for (col=0; col<9; col++)
	    {
		if (!m_initMask[row][col])
		    count++;
		    
	    }
	}
	return count;
    }
    
    private void checkStrategie(){
        if(student != null){
            student.inferer(m_lastRow, m_lastCol, m_table);
        }
    }
}
