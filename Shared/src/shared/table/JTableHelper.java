/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.table;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * JTableHelper
 *
 * Created on 03.11.2008, 20:58:32
 *
 *  Copyright (C) 03.11.2008  <reiner>
 *
 * @author reiner
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


public class JTableHelper
{
    public static void selectRow(JTable table, int row, boolean bScroll)
    {
		assert (row >= 0 && row < table.getRowCount());
		if (row >= 0 && row < table.getRowCount())
		{
			table.setRowSelectionInterval(row, row);
			if (bScroll)
			{
				Rectangle rect = table.getCellRect(row, 0, true);
				table.scrollRectToVisible(rect);
			}
		}
	}
	
    public static void setTableHeaderCellRenderer(TableColumn tableCol)
    {
		tableCol.setHeaderRenderer(new DefaultTableCellRenderer()
		{
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				if (table != null)
				{
					JComponent c = (JComponent)table.getTableHeader().getDefaultRenderer();
					setForeground(c.getForeground());
					setBackground(c.getBackground());
					setFont(c.getFont());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
					// setBorder(c.getBorder());
				}
				setText(value!= null ? value.toString() : "");
				// setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				setHorizontalAlignment(JLabel.CENTER);
				return this;
			}
		});
    }
	
	public static void setTableHeaderCellRenderer(JTable table)
	{
		TableColumnModel colModel = table.getColumnModel();
		int i, anzahl = colModel.getColumnCount();
		for (i=0; i<anzahl; i++)
		    setTableHeaderCellRenderer(colModel.getColumn(i));
	}
}

