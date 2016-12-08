/*
 * JAAProgressBar.java
 *
 * Created on 7. Mai 2006, 19:29
 *
 *  Copyright (C) 7. Mai 2006  <reiner>
 
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

import javax.swing.JProgressBar;
import javax.swing.BoundedRangeModel;

/**
 *
 * @author reiner
 */

public class JAAProgressBar extends JProgressBar
{
    
    /** Creates a new instance of JAAProgressBar */
    
    public JAAProgressBar()
    {
        super();
    }
    
    public JAAProgressBar(int orient)
    {
        super(orient);
    }

    public JAAProgressBar(int min, int max)
    {
        super(min, max);
    }

    public JAAProgressBar(int orient, int min, int max)
    {
        super(orient, min, max);
    }
    
    public JAAProgressBar (BoundedRangeModel newModel)
    {
        super(newModel);
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);
    }
}
