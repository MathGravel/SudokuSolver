/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.table;


/**
 * JTableColSortData
 *
 * Created on 03.11.2008, 19:56:28
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


public class JTableColSortData
{
    private int m_sortCol;
    private boolean m_sortUp;
    
    public JTableColSortData(int sortCol, boolean bSortUp)
    {
		m_sortCol = sortCol;
		m_sortUp = bSortUp;
    }
    
    public void setData(JTableColSortData data)
    {
		m_sortCol = data.m_sortCol;
		m_sortUp = data.m_sortUp;
    }
	
	public int getSortCol()
	{
		return m_sortCol;
	}

	public void setSortCol(int sortCol)
	{
		m_sortCol = sortCol;
	}

	public boolean getSortUp()
	{
		return m_sortUp;
	}
    
	public void setSortUp(boolean sortUp)
	{
		m_sortUp = sortUp;
	}
	
	public boolean equals (JTableColSortData data)
    {
		return (m_sortCol == data.m_sortCol && m_sortUp == data.m_sortUp);
    }
}

