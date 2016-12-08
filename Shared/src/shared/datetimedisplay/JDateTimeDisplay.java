/*
 * JDateTimeDisplay.java
 *
 * Created on 20. November 2006, 23:28
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


package shared.datetimedisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;

import java.text.DateFormat;

import javax.swing.Timer;

import java.util.Date;
import shared.aa.JAALabel;

/**
 * Display for date and/or time
 * designed to work with JStatusBar
 * @author reiner
 */

public class JDateTimeDisplay extends JAALabel
{
    protected Timer m_timer;
    
    public static final int DATETIMEDISPLAY_DATE_NONE = 0;
    public static final int DATETIMEDISPLAY_DATE_SHORT = 1;
    public static final int DATETIMEDISPLAY_DATE_MEDIUM = 2;
    public static final int DATETIMEDISPLAY_DATE_LONG = 3;
    public static final int DATETIMEDISPLAY_DATE_FULL = 4;
    
    public static final int DATETIMEDISPLAY_TIME_NONE = 0;
    public static final int DATETIMEDISPLAY_TIME_SHORT = 1;
    public static final int DATETIMEDISPLAY_TIME_MEDIUM = 2;
    public static final int DATETIMEDISPLAY_TIME_LONG = 3;
    public static final int DATETIMEDISPLAY_TIME_FULL = 4;
    
    protected int m_dateFormat;
    protected int m_timeFormat;
    protected String m_separator = "  ";

    /** Creates a new instance of JDateTimeDisplay */
    public JDateTimeDisplay()
    {
        m_dateFormat = DATETIMEDISPLAY_DATE_NONE;
        m_timeFormat = DATETIMEDISPLAY_TIME_NONE;
        doInit();
    }
    
    public void setDateFormat(int dateFormat)
    {
    	m_dateFormat = dateFormat;
    }

    public int getDateFormat()
    {
        return m_dateFormat;
    }
    
    public void setTimeFormat(int timeFormat)
    {
        m_timeFormat = timeFormat;
    }

    public int getTimeFormat()
    {
        return m_timeFormat;
    }

    public void setSeparator(String separator)
    {
        m_separator = separator;
    }

    public String getSeparator()
    {
        return m_separator;
    }

    protected void doInit()
    {
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0), BorderFactory.createEtchedBorder()));
        setFont(getFont().deriveFont(11.0f));
        doUpdateDisplay();
        m_timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                doUpdateDisplay();
            }
        });
        m_timer.start();
    }
    
    protected void doUpdateDisplay()
    {
        String strDate = "";
        String strTime = "";

        Date date = new Date();

        if (m_dateFormat == DATETIMEDISPLAY_DATE_SHORT)
            strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        else if (m_dateFormat == DATETIMEDISPLAY_DATE_MEDIUM)
            strDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
        else if (m_dateFormat == DATETIMEDISPLAY_DATE_LONG)
            strDate = DateFormat.getDateInstance(DateFormat.LONG).format(date);
        else if (m_dateFormat == DATETIMEDISPLAY_DATE_FULL)
            strDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

        if (m_timeFormat == DATETIMEDISPLAY_TIME_SHORT)
            strTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        if (m_timeFormat == DATETIMEDISPLAY_TIME_MEDIUM)
            strTime = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date);
        if (m_timeFormat == DATETIMEDISPLAY_TIME_LONG)
            strTime = DateFormat.getTimeInstance(DateFormat.LONG).format(date);
        if (m_timeFormat == DATETIMEDISPLAY_TIME_FULL)
            strTime = DateFormat.getTimeInstance(DateFormat.FULL).format(date);

        setText(" " + strDate + m_separator + strTime + " ");
    }
}
