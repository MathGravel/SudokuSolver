/*
 * JStatusPopupMenu.java
 *
 * Created on 27. Dezember 2006, 21:01
 *
 *  Copyright (C) 27. Dezember 2006  <reiner>
 
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

import javax.swing.JPopupMenu;

import shared.statusbar.JStatusBar;

/**
 *
 * @author reiner
 */

public class JStatusPopupMenu extends JPopupMenu
{
    protected JStatusBar m_statusBar;

    /** Creates a new instance of JStatusPopupMenu */
    public JStatusPopupMenu(final JStatusBar statusBar)
    {
        this(null, statusBar);
    }
    
    public JStatusPopupMenu(String s, final JStatusBar statusBar)
    {
        super(s);
        m_statusBar = statusBar;
    }
    
    public void setVisible(boolean b)
    {
        if (b)
            m_statusBar.saveText();
        else 
            m_statusBar.setText(null);
        super.setVisible(b);
    }
}
