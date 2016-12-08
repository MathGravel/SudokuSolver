/*
 * JCheckListCellRenderer.java
 *
 * Created on 18. September 2007, 19:04
 *
 *  Copyright (C) 18. September 2007  <reiner>
 
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


package shared.checklist;

import java.awt.Component;

import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import javax.swing.UIManager;

/**
 *
 * @author reiner
 */

class JSeparatorListCellRenderer extends JLabel implements ListCellRenderer
{
    protected static EmptyBorder m_noBorder = new EmptyBorder(1, 1, 1, 1);
    
    public JSeparatorListCellRenderer()
    {
	super();
	setOpaque(true);
	setBorder(m_noBorder);
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
	JCheckListData data = (JCheckListData)value;
	assert data instanceof JCheckListData;
	if (data instanceof JCheckListData)
	{
	    setFont(list.getFont());
	    setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
	    setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
	    Border border = cellHasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : m_noBorder;
	    setBorder(border);
	    setText(data.toString());
	}
	return this;
    }
}

public class JCheckListCellRenderer extends JCheckBox implements ListCellRenderer
{
    protected static EmptyBorder m_noBorder = new EmptyBorder(1, 1, 1, 1);
    protected static JSeparatorListCellRenderer m_defRenderer = new JSeparatorListCellRenderer();
    
    public JCheckListCellRenderer()
    {
	super();
	setOpaque(true);
	setBorder(m_noBorder);
	/*
        javax.swing.UIDefaults uid = javax.swing.UIManager.getDefaults();
        java.util.Enumeration uidKeys = uid.keys();
      	while (uidKeys.hasMoreElements())
	{
	    Object aKey = uidKeys.nextElement();
	    Object aValue = uid.get(aKey);
	    String str = "KEY: "+aKey+", VALUE: "+aValue + "\n";
	    System.err.print(str);
	}
	*/
    }
    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
	JCheckListData data = (JCheckListData)value;
	assert data instanceof JCheckListData;
	if (data instanceof JCheckListData)
	{
	    if (!data.isSeparator())
	    {
		setFont(list.getFont());
		setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
		Border border = cellHasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : m_noBorder;
		//Border border = isSelected ? UIManager.getBorder("focusSelectedCellHighlightBorder") : m_noBorder;
		setBorder(border);
		setText(data.toString());
		setSelected(data.isChecked());
		return this;
	    }
	}
	return m_defRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
