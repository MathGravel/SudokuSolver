/*
 *  PopupMenuAdapter.java
 *
 *  Created on 24. April 2006, 22:40
 *
 *  Copyright (C) 24. April 2006  <Reiner>

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

import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

/**
 *
 * @author reiner
 */

public class PopupMenuAdapter implements PopupMenuListener
{
    
    /** Creates a new instance of PopupMenuAdapter */
    public PopupMenuAdapter()
    {
    }
    public void popupMenuCanceled(PopupMenuEvent ev) 
    {}

    public void popupMenuWillBecomeVisible(PopupMenuEvent ev) 
    {}

    public void popupMenuWillBecomeInvisible(PopupMenuEvent ev)
    {}
}
