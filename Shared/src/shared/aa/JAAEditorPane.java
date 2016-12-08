/*
 * JAAEditorPane.java
 *
 * Created on 6. Mai 2006, 13:17
 *
 *  Copyright (C) 6. Mai 2006  <reiner>
 
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


package shared.aa;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.io.IOException;

import java.net.URL;

import javax.swing.JEditorPane;


/**
 * Package to manually enable anti-aliasing
 * @author reiner
 */

public class JAAEditorPane extends JEditorPane
{
    
    /** Creates a new instance of JAAEditorPane */
    public JAAEditorPane()
    {
        super();
    }

    public JAAEditorPane(String url) throws IOException
    {
        super(url);
    }
    
    public JAAEditorPane(String type, String text) 
    {
        super(type, text);
    }
    
    public JAAEditorPane(URL url) throws IOException
    {
        super(url);
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        // seems to be no necessarry with JDK 1.6
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
    }
}
