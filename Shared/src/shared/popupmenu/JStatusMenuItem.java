/*
 * JStatusMenuItem.java
 *
 * Created on 20. November 2006, 22:17
 *
 *  Copyright (C) 20. November 2006  <reiner>
 
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

import shared.statusbar.JStatusBar;

/**
 *
 * @author reiner
 */

public class JStatusMenuItem extends JMenuItem
{
    protected JMenuItemHelper m_menuItemHelper;
    
    protected void doInit(String helpText, JStatusBar statusBar)
    {
        m_menuItemHelper = new JMenuItemHelper(this, helpText, statusBar);
    }
    
    /** Creates a new instance of JStatusMenuItem */
    public JStatusMenuItem(String menuText, String helpText, JStatusBar statusBar)
    {
        super(menuText);
        doInit(helpText, statusBar);
    }

    public JStatusMenuItem(String menuText, int mnemonic, String helpText, JStatusBar statusBar)
    {
        super(menuText, mnemonic);
        doInit(helpText, statusBar);
    }

    public void setText(String menuText, String helpText)
    {
        setText(menuText);
        m_menuItemHelper.setText(helpText);
    }
}
