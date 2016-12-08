/*
 * JStatusMenu.java
 *
 * Created on 20. November 2006, 21:58
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

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.event.PopupMenuEvent;

import shared.statusbar.JStatusBar;

/**
 *
 * @author reiner
 * use this class instead of JMenu if you want to add help text for menu items in the status bar
 */

class JMenuUpdateData
{
    public JMenuItem m_item;
    public JMenuItemUpdateHandler m_handler;
    
    JMenuUpdateData(JMenuItem item, JMenuItemUpdateHandler handler)
    {
        m_item = item;
        m_handler = handler;
    }
}

public class JStatusMenu extends JMenu
{
    ArrayList<JMenuUpdateData> m_updateArray = new ArrayList<JMenuUpdateData>();
    // protected JStatusBar m_statusBar;
    
    /** Creates a new instance of JStatusMenu */
    public JStatusMenu(String s, final JStatusBar statusBar)
    {
        super(s);
        final JPopupMenu popupMenu = getPopupMenu();
        popupMenu.addPopupMenuListener(new PopupMenuAdapter()
        {
            public void popupMenuWillBecomeVisible(PopupMenuEvent ev) 
            {
                statusBar.saveText();
                if (m_updateArray != null)
                {
                    for (JMenuUpdateData item : m_updateArray)
                    {
                        MenuElement[] items = popupMenu.getSubElements();
                        for (MenuElement mItem : items)
                        {
                            if (mItem instanceof JMenuItem)
                            {
                                if (mItem == item.m_item)
                                {
                                    item.m_handler.update((JMenuItem)mItem);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent ev)
            {
                statusBar.setText(null);
            }
        });
    }
    public void addUpdateHandler(JMenuItem item, JMenuItemUpdateHandler handler)
    {
        m_updateArray.add(new JMenuUpdateData(item, handler));
    }

    public void removeUpdateHandler(JMenuItemUpdateHandler handler)
    {
        Iterator<JMenuUpdateData> iter = m_updateArray.iterator();
        while(iter.hasNext())
        {
            if (iter.next().m_handler == handler)
                iter.remove();
        }
    }
}
