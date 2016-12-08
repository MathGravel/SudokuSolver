/*
 * JBrowserHelper.java
 *
 * Created on 24. November 2006, 20:00
 *
 *  Copyright (C) 24. November 2006  <reiner>
 
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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

import java.io.IOException;
import java.net.URL;

import shared.aa.JAAEditorPane;

/**
 *
 * @author reiner
 */

public class JBrowserHelper extends JDialog
{
    private JAAEditorPane m_browser;
    
    /** Creates a new instance of JBrowser */
    public JBrowserHelper(JFrame frame, URL url)
    {
        super(frame, false);
        final JDialog dia = this;

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        try
        {
            m_browser = new JAAEditorPane(url);
        }
        catch(IOException ex)
        {
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

        setResizable(true);
    }
}
