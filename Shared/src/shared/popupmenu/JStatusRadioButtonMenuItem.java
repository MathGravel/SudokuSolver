/*
 * JStatusRadioButtonMenuItem.java
 *
 * Created on 20. November 2006, 22:34
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

import javax.swing.JRadioButtonMenuItem;

import shared.statusbar.JStatusBar;

/**
 *
 * @author reiner
 */

public class JStatusRadioButtonMenuItem extends JRadioButtonMenuItem
{
    
    protected void doInit(String helpText, JStatusBar statusBar)
    {
        JMenuItemHelper menuItemHelper = new JMenuItemHelper(this, helpText, statusBar);
    }

    /** Creates a new instance of JStatusRadioButtonMenuItem */
    public JStatusRadioButtonMenuItem(String menuText, boolean selected, String helpText, JStatusBar statusBar)
    {
        super(menuText, selected);
        doInit(helpText, statusBar);
    }

    public JStatusRadioButtonMenuItem(String menuText, String helpText, JStatusBar statusBar)
    {
        super(menuText);
        doInit(helpText, statusBar);
    }
}
