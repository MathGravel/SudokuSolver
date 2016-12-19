/*
 * JMainFrame.java
 *
 * Created on 26. Oktober 2007, 22:23
 *
 *  Copyright (C) 26. Oktober 2007  <reiner>
 
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


/**
 *
 * @author reiner
 */

package jsudoku;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
    
import java.net.URL;
import java.net.URLConnection;

import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.SwingUtilities;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// our shared package
import shared.classpathhelper.JClassPathHelper;
import shared.datetimedisplay.JDateTimeDisplay;
import shared.files.JPathHelper;
import shared.files.JMyFileFilter;
import shared.jinfohelper.JInfoHelper;
import shared.statusbar.JStatusBar;
import shared.popupmenu.JStatusMenu;
import shared.popupmenu.JStatusMenuItem;
import shared.popupmenu.JStatusRadioButtonMenuItem;
import shared.browser.JBrowser;
import shared.browserlaunch.JBrowserLaunch;
import tutorial.student.Student;


public final class JMainFrame extends JFrame
{
    
    private JStatusBar m_statusBar;
    private JDateTimeDisplay m_dateTimeDisplay;
    private JSudokuGrid m_grid;
    private JSudokuTable m_table = new JSudokuTable();
    private JMenuBar m_menuBar;
    private PageFormat m_pageFormat = null;
    private Student student = null;


    private static final String[] m_homepage = {"http://mitglied.lycos.de/jsudokucreator/", "http://sourceforge.net/projects/jsudokucreator/"};
    private static final String m_updateFile = "http://mitglied.lycos.de/jsudokucreator/update.php";

    /** Creates a new instance of JMainFrame */
    public JMainFrame()
    {
        super("");
        setIconImage(Main.m_icon16.getImage());
        setTitle(Main.getString("caption"));

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent ev)
            {
                doSaveSettings();
            }
        });

        m_table.create(0);
        JPanel panel = new JPanel();
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(panel, BorderLayout.CENTER);

        m_statusBar = new JStatusBar(Main.getString("welcome"));
        m_dateTimeDisplay = new JDateTimeDisplay();
        m_dateTimeDisplay.setTimeFormat(JDateTimeDisplay.DATETIMEDISPLAY_TIME_MEDIUM);
        m_dateTimeDisplay.setDateFormat(JDateTimeDisplay.DATETIMEDISPLAY_DATE_SHORT);
        m_dateTimeDisplay.setSeparator(" - ");

        JPanel south = new JPanel();
        south.setLayout(new BorderLayout(0, 0));
        south.add(m_statusBar, BorderLayout.CENTER);
        south.add(m_dateTimeDisplay, BorderLayout.EAST);
        cp.add(south, BorderLayout.SOUTH);

        m_grid = new JSudokuGrid(m_table);
        cp.add(m_grid, BorderLayout.CENTER);

        JHelpDesk test = new JHelpDesk();
        //cp.add(test,BorderLayout.EAST);
        
        m_menuBar = new JMenuBar();
        setJMenuBar(m_menuBar);

        // Sudoku menu
        JStatusMenu menu = new JStatusMenu(Main.getString("menu_sudoku"), m_statusBar);
        m_menuBar.add(menu);
        JStatusMenuItem menuItem;

        // java.security.Provider[] provider = java.security.Security.getProviders();

        // Create
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_create"), Main.getString("statusbar_menu_sudoku_create_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                m_grid.removeTextField(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                m_table.create(0);
                m_grid.initMask(true);
                setCursor(Cursor.getDefaultCursor());
                System.err.println("Corr: " + m_table.m_corr);
                }
        });

        // New
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_new"), Main.getString("statusbar_menu_sudoku_new_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                m_grid.removeTextField(true);
                m_table.init();
                m_grid.initMask(true);
            }
        });

        // Solve
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_solve"), Main.getString("statusbar_menu_sudoku_solve_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                m_grid.removeTextField(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                boolean flag = m_table.solve();
                setCursor(Cursor.getDefaultCursor());
                m_grid.repaint();
                if (!flag)
                    JOptionPane.showMessageDialog(Main.m_mainFrame, Main.getString("msg_unabletosolve"), Main.getMessageBoxCaption(), JOptionPane.WARNING_MESSAGE, null);
            }
        });

        // Check
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_check"), Main.getString("statusbar_menu_sudoku_check_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                m_grid.removeTextField(true);
                m_table.checkValid(m_grid.getInvalidMask());
                m_grid.repaint();
            }
        });

        // Reset
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_reset"), Main.getString("statusbar_menu_sudoku_reset_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                m_grid.removeTextField(true);
                m_grid.reset();
                m_grid.repaint();
            }
        });

        menu.addSeparator();

        // Open
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_open"), Main.getString("statusbar_menu_sudoku_open_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                //File file = new File(data.getFilePath());
                JFileChooser fileChooser = new JFileChooser(Main.m_settings.getSudokuDirectory());
                fileChooser.setDialogTitle(Main.getString("caption_open"));
                FileFilter allFilter = fileChooser.getAcceptAllFileFilter();
                fileChooser.setAcceptAllFileFilterUsed(false);
                JMyFileFilter fileFilter = new JMyFileFilter();
                    fileFilter.addExtension(Main.m_settings.getSudokuFileExtension());
                fileFilter.setDescription(Main.getString("filefilter_sdk"));
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.addChoosableFileFilter(allFilter);
                fileChooser.setFileFilter(fileFilter);
                if (fileChooser.showOpenDialog(Main.m_mainFrame) == JFileChooser.APPROVE_OPTION)
                {
                    m_grid.removeTextField(true);
                    if (doLoad(fileChooser.getSelectedFile()))
                    m_grid.repaint();
                }
            }
        });

        // Save
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_save"), Main.getString("statusbar_menu_sudoku_save_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                //File file = new File(data.getFilePath());
                JFileChooser fileChooser = new JFileChooser(Main.m_settings.getSudokuDirectory());
                fileChooser.setSelectedFile(new File(m_table.getHash() + "." + Main.m_settings.getSudokuFileExtension()));
                fileChooser.setDialogTitle(Main.getString("caption_save"));
                FileFilter allFilter = fileChooser.getAcceptAllFileFilter();
                fileChooser.setAcceptAllFileFilterUsed(false);
                JMyFileFilter fileFilter = new JMyFileFilter();
                    fileFilter.addExtension(Main.m_settings.getSudokuFileExtension());
                fileFilter.setDescription(Main.getString("filefilter_sdk"));
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.addChoosableFileFilter(allFilter);
                fileChooser.setFileFilter(fileFilter);
                if (fileChooser.showSaveDialog(Main.m_mainFrame) == JFileChooser.APPROVE_OPTION)
                {
                    m_grid.removeTextField(true);
                    File file = fileChooser.getSelectedFile();
                    if (JPathHelper.getFileExtension(file).length() == 0)
                    file = new File(file.getPath() + "." + Main.m_settings.getSudokuFileExtension());
                    doSave(file);
                }
            }
        });

        menu.addSeparator();

        // Print
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_sudoku_print"), Main.getString("statusbar_menu_sudoku_print_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                final PrinterJob printerJob = PrinterJob.getPrinterJob();
                if (m_pageFormat == null) m_pageFormat = printerJob.defaultPage();
                PageFormat pf = printerJob.pageDialog(m_pageFormat);
                if (pf != m_pageFormat)
                {
                    m_pageFormat = pf;
                    printerJob.setPrintable(m_grid, m_pageFormat);
                    printerJob.setJobName(Main.getString("caption"));
                    if (printerJob.printDialog())
                    {
                    m_grid.removeTextField(true);
                    if (!m_table.isComplete() && JOptionPane.showConfirmDialog(Main.m_mainFrame, Main.getString("msg_print_resolved_ask"), Main.getMessageBoxCaption(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Main.m_icon32) == JOptionPane.YES_OPTION)
                        m_grid.setPrintSolved(true);
                    else
                        m_grid.setPrintSolved(false);
                    Thread thread = new Thread ()
                    {
                        public void run()
                        {
                        try
                        {
                            JSudokuTable sk = new JSudokuTable(m_table);
                            printerJob.print();
                            m_grid.setPrintFooter(true);
                            m_table.set(sk);
                        }
                        catch (PrinterException ex)
                        {}
                        finally
                        {
                            SwingUtilities.invokeLater(new Runnable()
                            {
                            public void run()
                            {
                                m_grid.repaint();
                                m_statusBar.setText(Main.getString("statusbar_print_end"));
                                setCursor(Cursor.getDefaultCursor());
                            }
                            });
                        }
                        }
                    };
                    m_statusBar.setText(Main.getString("statusbar_print_start"));
                    setCursor(Cursor.getDefaultCursor());
                    thread.start();
                    }
                }
            }
        });
        
        menu = new JStatusMenu(Main.getString("menu_strategy"), m_statusBar);
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_strategy_square"), Main.getString("statusbar_menu_strategy_square"), m_statusBar);
        menu.add(menuItem);
         menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev){
                m_grid.removeTextField(true);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                m_table.create(0);
                m_grid.initMask(true);
                setCursor(Cursor.getDefaultCursor());
                System.err.println("Corr: " + m_table.m_corr);
                JSudokuTable clone = m_table.clone();
                clone.doSolve();
                m_table.m_solution = clone.m_data;
            }
        });

        menu = new JStatusMenu(Main.getString("menu_extra"), m_statusBar);
        m_menuBar.add(menu);

        // Settings
        menuItem = new JStatusMenuItem(Main.getString("menu_extra_settings"), Main.getString("statusbar_menu_extra_settings_help"), m_statusBar);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                JSettingsDialog dia = new JSettingsDialog(Main.m_mainFrame);
                if (dia.doModal())
                {
                    doSaveSettings();
                    m_grid.repaint();
                }
            }
        });

        // Look and Feel
        final JMenu lfMenu = new JMenu(Main.getString("menu_extra_lookandfeel"));
        LookAndFeelInfo[] lfArray = UIManager.getInstalledLookAndFeels();
        JStatusRadioButtonMenuItem radioButtonMenuItem;
        int count = 0;
        for (LookAndFeelInfo lfInfo : lfArray)
        {
            radioButtonMenuItem = new JStatusRadioButtonMenuItem(lfInfo.getName(), String.format(Main.getString("statusbar_menu_extra_lf_help"), lfInfo.getName()), m_statusBar);
            radioButtonMenuItem.setActionCommand(lfInfo.getClassName());
            radioButtonMenuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev)
                {
                    try
                    {
                        Main.m_settings.setLookAndFeel(ev.getActionCommand());
                        int i, anzahl = lfMenu.getItemCount();
                        for (i=0; i< anzahl; i++)
                        {
                            JStatusRadioButtonMenuItem menuItem = (JStatusRadioButtonMenuItem)lfMenu.getItem(i);
                            menuItem.setSelected(menuItem.getActionCommand().equals(ev.getActionCommand()));
                        }
                    }
                    catch(Exception ex)
                    {
                    }
                    // Restart
                    if (JOptionPane.showConfirmDialog(Main.m_mainFrame, Main.getString("msg_lookandfeel_restart_ask"), Main.getMessageBoxCaption(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                    {
                        try
                        {
                            String path = JClassPathHelper.getClassPath(this, true);
                            if (path != null)
                            {
                                doSaveSettings();
                                setVisible(false);
                                path = JPathHelper.addSeparator(path);
                                path += "jstart.jar";
                                String[] cmdArray = {JInfoHelper.getJavaExecutablePath(), "-jar", path};
                                if (Main.m_settings.isDebug())
                                {
                                    System.err.println(cmdArray[0]);
                                    System.err.println(path);
                                }
                                Runtime.getRuntime().exec(cmdArray);
                                System.exit(0);
                            }
                        }
                        catch(Exception ex)
                        {
                            setVisible(true);
                            System.err.println(ex);
                        }
                    }
                }
            });
            radioButtonMenuItem.setSelected(Main.m_settings.getLookAndFeel().equals(lfInfo.getClassName()));
            lfMenu.add(radioButtonMenuItem);
        }
        menu.add(lfMenu);


        menu = new JStatusMenu(Main.getString("menu_help"), m_statusBar);
        m_menuBar.add(menu);

        // Help
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_help_help"), Main.getString("statusbar_menu_help_help_help"), m_statusBar);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                JBrowser browser = new JBrowser(Main.m_mainFrame, Main.getString("help_caption"), getClass().getResource(Main.getString("helpfile")));
                browser.setVisible(true);
            }
        });

        // Homepage
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_help_homepage"), Main.getString("statusbar_menu_help_homepage_help"), m_statusBar);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                if (!JBrowserLaunch.openURL(m_homepage[0]))
                {
                    JOptionPane.showMessageDialog(Main.m_mainFrame, String.format(Main.getString("msg_browserlaunch_failed"), m_homepage[0], m_homepage[1]), Main.getMessageBoxCaption(), JOptionPane.PLAIN_MESSAGE, Main.m_icon32);
                }
            }
        });

        menu.addSeparator();

        // Update
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_help_update"), Main.getString("statusbar_menu_help_update_help"), m_statusBar);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                doCheckForUpdates(true);
            }
        });

        menu.addSeparator();

        // About
        m_menuBar.add(menu);
        menuItem = new JStatusMenuItem(Main.getString("menu_help_about"), Main.getString("statusbar_menu_help_about_help"), m_statusBar);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append(Main.m_versionString);
                strBuilder.append("\n\n");
                strBuilder.append("Homepage:\n");
                strBuilder.append(m_homepage[0]);
                strBuilder.append("\n");
                strBuilder.append(m_homepage[1]);
                strBuilder.append("\n\n");
                strBuilder.append(JInfoHelper.getJavaInfo(Main.m_settings.isDebug() ? 2 : 1));
                if (Main.m_settings.isDebug())
                    strBuilder.append("\n\nMemory: (total, free, max): " + Runtime.getRuntime().totalMemory() + ", " + Runtime.getRuntime().freeMemory() + ", " + Runtime.getRuntime().maxMemory());
                JOptionPane.showMessageDialog(Main.m_mainFrame, strBuilder.toString(), Main.getMessageBoxCaption(), JOptionPane.PLAIN_MESSAGE, Main.m_icon32);
            }
        });

        setPreferredSize(new Dimension(400, 400));
        setResizable(true);
        pack();

        if (Main.m_settings.isCheckUpdate())
        {
            Thread thread = new Thread()
            {
                public void run()
                {
                    doCheckForUpdates(false);
                }
            };
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
    }
    
    public void doCheckForUpdates(boolean bManual)
    {
        boolean flag = false;
        try
        {
            if (bManual)
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            URL url = new URL(m_updateFile);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String str;
            StringBuilder strBuilder = new StringBuilder();;
            while ((str = bufReader.readLine()) != null)
            strBuilder.append(str);
            bufReader.close();
            Pattern pat = Pattern.compile("jsudocucreator_version=(\\d+)\\.(\\d+)");
            Matcher m = pat.matcher(strBuilder.toString());
            int minVer, majVer;
            int minVerThis, majVerThis;
            if (m.find())
            {
                majVer = Integer.parseInt(m.group(1));
                minVer = Integer.parseInt(m.group(2));
                pat = Pattern.compile("(\\d+)\\.(\\d+)");
                m = pat.matcher(Main.m_version);
                if (m.find())
                {
                    majVerThis = Integer.parseInt(m.group(1));
                    minVerThis = Integer.parseInt(m.group(2));
                    if (bManual)
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    if (majVerThis < majVer || (majVerThis == majVer && minVerThis < minVer))
                    {
                        final String msg = String.format(Main.getString("msg_update"), majVerThis, minVerThis, majVer, minVer);
                        final JMainFrame main = this;
                        if (bManual)
                            JOptionPane.showMessageDialog(this, msg, Main.getMessageBoxCaption(), JOptionPane.WARNING_MESSAGE);
                        else
                            SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    JOptionPane.showMessageDialog(main, msg, Main.getMessageBoxCaption(), JOptionPane.WARNING_MESSAGE);
                                }
                            });
                    }
                    else if (bManual && (majVerThis > majVer || (majVerThis == majVer && minVerThis > minVer)))
                    JOptionPane.showMessageDialog(this, String.format(Main.getString("msg_update_noupdate_beta"), majVerThis, minVerThis), Main.getMessageBoxCaption(), JOptionPane.INFORMATION_MESSAGE);
                    else if (bManual)
                    JOptionPane.showMessageDialog(this, String.format(Main.getString("msg_update_noupdate"), majVerThis, minVerThis), Main.getMessageBoxCaption(), JOptionPane.INFORMATION_MESSAGE);
                    flag = true;
                }
            }
        }
        catch (IOException ex)
        {
            if (Main.m_settings.isDebug())
            System.err.println("Unable to get update infos");
        }
        if (bManual)
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (!flag && bManual)
            JOptionPane.showMessageDialog(this, Main.getString("msg_update_err"), Main.getMessageBoxCaption(), JOptionPane.ERROR_MESSAGE);
    }

    private void doSaveSettings()
    {   
    	Main.m_settings.save(JPathHelper.addSeparator(System.getProperty("user.home")) + JSettings.SETTINGS_FILE);
    }
    
    private boolean doSave(File file)
    {
        boolean flag = false;
        Properties prop = new Properties();

        int row, col;
        for (row=0; row<9; row++)
        {
            StringBuffer buf = new StringBuffer();
            StringBuffer bufInitMask = new StringBuffer();
            StringBuffer bufInvalidMask = new StringBuffer();
            boolean [][] initMask = m_grid.getInitMask();
            boolean [][] invalidMask = m_grid.getInvalidMask();
            for (col=0; col<9; col++)
            {
                if (col != 0)
                {
                    buf.append(",");
                    bufInitMask.append(",");
                    bufInvalidMask.append(",");
                }
                buf.append(m_table.m_data[row][col].toString());
                bufInitMask.append(Integer.toString(initMask[row][col] ? 1 : 0));
                bufInvalidMask.append(Integer.toString(invalidMask[row][col] ? 1 : 0));
            }
            String str = String.format("row_%1$d", row);
            prop.setProperty(str, buf.toString());
            str = String.format("initMask_%1$d", row);
            prop.setProperty(str, bufInitMask.toString());
            str = String.format("invalidMask_%1$d", row);
            prop.setProperty(str, bufInvalidMask.toString());
        }
        prop.setProperty("ID", m_table.getHash());
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(file);
            prop.store(out, "JSudoku");
            flag = true;
        }
        catch (IOException ex)
        {
        }
        finally
        {
            try
            {
                if (out != null) out.close();
            }
            catch (IOException ex)
            {}
        }
        return flag;
    }
    
    private boolean doLoad(File file)
    {
        boolean flag = false;
        FileInputStream in = null;
        try
        {
            Properties prop = new Properties();
            in = new FileInputStream(file);
            prop.load(in);
            m_table.setHash(prop.getProperty("ID", ""));
            boolean [][] initMask = m_grid.getInitMask();
            boolean [][] invalidMask = m_grid.getInvalidMask();
            int row, col;
            for (row=0; row<9; row++)
            {
                String str = String.format("row_%1$d", row);
                str = prop.getProperty(str, "");
                String[] strArray = str.split(",");
                str = String.format("initMask_%1$d", row);
                str = prop.getProperty(str, "");
                String[] strArrayInitMask = str.split(",");
                str = String.format("invalidMask_%1$d", row);
                str = prop.getProperty(str, "");
                String[] strArrayInvalidMask = str.split(",");
                if (strArray.length >= 9)
                {
                    for (col=0; col<9; col++)
                    {
                        m_table.m_data[row][col].setNum(Integer.parseInt(strArray[col]));
                        initMask[row][col] = Integer.parseInt(strArrayInitMask[col]) != 0;
                        invalidMask[row][col] = Integer.parseInt(strArrayInvalidMask[col]) != 0;
                    }
                }
                flag = true;
            }
        }
        catch(IOException ex)
        {}
        finally
        {
            try
            {
                if (in != null) in.close();
            }
            catch (IOException ex)
            {}
        }
        return flag;
    }
}
