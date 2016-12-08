/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author reiner
 */
public abstract class JTableDataModel<T> extends AbstractTableModel implements Comparator<T>
{
    protected ArrayList<T> m_data;
    protected JTableColSortData m_sortData;
    
    public JTableDataModel()
    {
        doInit(0, true);
    }

    public JTableDataModel(int sortCol, boolean bSortUp)
    {
        doInit(sortCol, bSortUp);
    }
    
    protected void doInit(int sortCol, boolean bSortUp)
    {
        m_data = new ArrayList<T>();
        m_sortData = new JTableColSortData(sortCol, bSortUp);
    }
    
	public abstract Object getValueAt(int row, int col);
    public abstract int getColumnCount();

    public int getSortCol()
    {
		return m_sortData.getSortCol();
    }

    public boolean getSortUp()
    {
		return m_sortData.getSortUp();
    }
    
    public void setSortCol(int col)
    {
		m_sortData.setSortCol(col);
    }

    public void setSortUp(boolean bUp)
    {
		m_sortData.setSortUp(bUp);
    }
	
    public Icon getColIcon(int col)
    {
        return null;
    }
    
    public T getItem(int row)
    {
        assert (row >=0 && row < m_data.size());
        return (row >= 0 && row < m_data.size() ? m_data.get(row) : null);
    }

    public int findRow(T data)
    {
		int i=0;
		for (T item : m_data)
		{
			if (data == item)
				return i;
			i++;
		}
		return 0;
    }

	public void setData(ArrayList<T> data)
    {
        m_data = data;
		fireTableDataChanged();
		sort();
    }

    public ArrayList<T> getData()
    {
        return m_data;
    }

    public T getRowData(int row)
    {
        assert (row >= 0 && row < m_data.size());
		return (row >= 0 && row < m_data.size() ? m_data.get(row) : null);
	}
    
    public int getRowCount()
    {
        return m_data.size();
    }
         
	public void sort()
    {
		Collections.sort(m_data, this);
		fireTableDataChanged();
    }
    
	public int compare(T data1, T data2)
	{
		int h = 0;
		switch(m_sortData.getSortCol())
		{
			case 0:
				h = data1.toString().compareTo(data2.toString());
				break;
			default:
                // Override the compare function and do what to do!
				assert (false);
		}
		if (!m_sortData.getSortUp())
			h = -h;
		return h;
	}

	public boolean equals(Object obj)
	{
		boolean flag = false;

		if (obj instanceof JTableDataModel)
		{
			JTableDataModel comp = (JTableDataModel)obj;
			if (comp.m_sortData.equals(comp.m_sortData))
				flag = true;
		}
		return flag;
	}
	
	public void updateColHeader(JTable table)
	{
		int i, anzahl = getColumnCount();
		TableColumnModel colModel = table.getColumnModel();
		for (i=0; i<anzahl; i++)
		{
			TableColumn tableCol = colModel.getColumn(i);
			JLabel label = (JLabel)tableCol.getHeaderRenderer();
			assert (label != null);
			if (label != null)
				label.setIcon(getColIcon(i));
		}
		table.getTableHeader().repaint();
	}
}
