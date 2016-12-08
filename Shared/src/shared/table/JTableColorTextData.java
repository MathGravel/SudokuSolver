/**
 * JTableColorTextData
 *
 *  Created on 05.11.2008, 18:40:51
 *
 *  Copyright (C) 05.11.2008  <reiner>
 *
 *  @author reiner
 *
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


package shared.table;

import java.awt.Color;
import javax.swing.JTable;



public class JTableColorTextData
{
	private Color m_textColor = null;
	private Color m_backColor = null;
	private String m_text;
	private int m_row = -1;
	
	public JTableColorTextData()
	{
		JTable table = new JTable();
		m_textColor = table.getForeground();
		m_backColor = table.getBackground();
		
		/*
		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
		m_textColor = cr.getForeground();
		m_backColor = cr.getBackground();
		*/
	}
	
	public String toString()
	{
		return (m_text !=  null ? m_text : "");
	}

	public void setTextColor(Color color)
	{
		m_textColor = color;
	}
	public Color getTextColor()
	{
		return m_textColor;
	}

	public void setBackColor(Color color)
	{
		m_backColor = color;
	}
	public Color getBackColor()
	{
		return m_backColor;
	}
	
	public void setText(String text)
	{
		m_text = text;
	}
	public String getText()
	{
		return m_text;
	}
	
	public int getRow()
	{
		return m_row;
	}

	public void setRow(int row)
	{
		m_row = row;
	}
}
