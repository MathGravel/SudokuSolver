/*
 * JCheckList.java
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

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
/**
 *
 * @author reiner
 */

public class JCheckList extends JList
{
    
    /** Creates a new instance of JCheckList */
    public JCheckList()
    {
        super (new DefaultListModel());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new JCheckListCellRenderer());
        JCheckListListener checkListener = new JCheckListListener(this);
        addMouseListener(checkListener);
        addKeyListener(checkListener);
    }
    
    public DefaultListModel getModel() 
    {
    	return (DefaultListModel)super.getModel();
    }
}
