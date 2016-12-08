/*
 * JBrowser.java
 *
 *  Created on 1. Mai 2006, 22:31
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


package shared.browser;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import java.io.IOException;
import java.net.URL;
    
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

import shared.aa.JAAEditorPane;

/**
 *
 * @author reiner
 */


public class JBrowser extends JDialog
{
    private JAAEditorPane m_browser;

    
    /** Creates a new instance of JBrowser */
    public JBrowser(JFrame frame, String caption, URL helpUrl)
    {
        super(frame, false);
        final JDialog dia = this;

        setTitle(caption);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        try
        {
            m_browser = new JAAEditorPane(helpUrl);
        }
        catch(IOException ex)
        {
            // if (Main.m_settings.isDebug())
            // System.err.println("Unable to open help!");
        }
        m_browser.setEditable(false);
        m_browser.addHyperlinkListener(new HyperlinkListener()
        {
            public void hyperlinkUpdate(HyperlinkEvent ev)
            {
                URL url = ev.getURL();
                if (url != null && ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    try
                    {
                    m_browser.setPage(url);
                    }
                    catch(IOException ex)
                    {
                    }
                }
            }
        });
        JScrollPane pane = new JScrollPane(m_browser);
        cp.add(pane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(820, 700));
        setResizable(true);
        Point pt = frame.getLocationOnScreen();
        setLocation((int)pt.getX() + 20, (int)pt.getY() + 20);
    //	setLocationRelativeTo(frame);
        pack();
    }
}
