/*
 * JCheckListData.java
 *
 * Created on 18. September 2007, 18:53
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

/**
 *
 * @author reiner
 */

public class JCheckListData<T>
{
    
    private T m_data = null;
    private String m_label = "";
    private boolean m_bChecked = false;
    private boolean m_bIsSeparator = false;
    
    /** Creates a new instance of JCheckListData */
    public JCheckListData()
    {
    }
    
    public void setData(T data)
    {
	m_data = data;
    }
    
    public T getData()
    {
	return m_data;
    }

    public String toString()
    {
	return m_label;
    }

    public void setLabel(String label)
    {
	m_label = label;
    }

    public void setChecked(boolean bChecked)
    {
	m_bChecked = bChecked;
    }

    public boolean isChecked()
    {
	return m_bChecked;
    }
    
    public void setIsSeparator(boolean bIsSeparator)
    {
	m_bIsSeparator = bIsSeparator;
    }

    public boolean isSeparator()
    {
	return m_bIsSeparator;
    }
}
