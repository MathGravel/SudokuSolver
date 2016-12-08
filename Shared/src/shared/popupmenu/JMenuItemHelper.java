/*
 *  JMenuItemHelper.java
 *
 *  Created on 1. Mai 2006, 09:58
 *
 *  Copyright (C) 1. Mai 2006  <Reiner>

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


package shared.popupmenu;

import javax.swing.JMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


import shared.statusbar.JStatusBar;

/**
 *
 * @author reiner
 */

public class JMenuItemHelper implements ChangeListener
{
    private JMenuItem m_menuItem;
    private String m_txt;
    private JStatusBar m_statusBar;
    public JMenuItemHelper(JMenuItem menuItem, String txt, JStatusBar statusBar)
    {
        m_statusBar = statusBar;
        m_menuItem = menuItem;
        m_txt = txt;
        menuItem.addChangeListener(this);
    }
    
    public void stateChanged(ChangeEvent ev)
    {
    	m_statusBar.setTempText(m_menuItem.isArmed() ? m_txt : "");
    }
    
    public void setText(String txt)
    {
        m_txt = txt;
    }
}