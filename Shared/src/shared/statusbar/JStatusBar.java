/*
 *  JStatusBar.java
 *
 *  Created on 24. April 2006, 21:56
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


package shared.statusbar;


import java.awt.Dimension;

import shared.aa.JAALabel;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

/**
 *
 * implements a status bar based on JAALabel
 * there is a feature to save the last message text and to restore it
 * this is used e.g. for displaying help messages during a  popup menu is open
 * @see shared.popupmenu.JMenuItemHelper
 * we set per default borders and a small font
 */

public class JStatusBar extends JAALabel
{
    
    private String m_oldTxt = " ";
    
    /** Creates a new instance of JStatusBar */
    protected void doInit()
    {
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0), BorderFactory.createEtchedBorder()));
        setFont(getFont().deriveFont(11.0f));
    }
    
    /** Creates a new instance of JStatusBar */
    public JStatusBar()
    {
        doInit();
        setText(null);
    }

    /** Creates a new instance of JStatusBar
     * @param txt is the text displayed at the beginning in the status bar
     */
    public JStatusBar(String txt)
    {
        doInit();
        setText(txt);
    }
    
    /**
     * save the latest message 
     */
    public void saveText()
    {
        m_oldTxt = getText();
    }
    
    /**
     * sets a message text in the JLabel, and save it as the last message
     * we will use this for normal status bar messages.<br>
     * We will call <code>setText(null)</code> after a menu has closed to restore the latest message.
     * @param txt the message text
     */
    @Override
    public void setText(String txt)
    {
        String str;
        if (txt != null && txt.length() != 0)
        {
            str = txt;
    //	    m_oldTxt = getText();
            m_oldTxt = str;
        }
        else str = m_oldTxt;
        super.setText(prepareText(str));
        Dimension dim = getSize();
        paintImmediately(0, 0, dim.width, dim.width);
    }

    /**
     * sets a message text in the JLabel, but do not save this text
     * we will use this for help messages if a menu is open
     * @param txt the message text
     */
    public void setTempText(String txt)
    {
        String str;
        if (txt != null && txt.length() != 0)
        {
            str = txt;
        }
        else str = m_oldTxt;
        super.setText(prepareText(str));
        Dimension dim = getSize();
        paintImmediately(0, 0, dim.width, dim.width);
    }
    
    private String prepareText(String str)
    {
        if (str != null)
        {
            str = str.trim();
            if (str.toLowerCase().indexOf("<html>") < 0)
            str = " " + str;
        }
        return str;
    }
}
