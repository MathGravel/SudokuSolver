/*
 * JProgBar.java
 *
 * Created on 7. Mai 2006, 19:25
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


package shared.progbar;

import shared.aa.JAAProgressBar;
import java.awt.Dimension;


/**
 *
 * @author reiner
 */

public class JProgBar extends JAAProgressBar
{
    
    /** Creates a new instance of JProgBar */
    public JProgBar()
    {
        super();
        reset();
    }
    
    public JProgBar(int orient)
    {
        super(orient);
        reset();
    }

    public JProgBar(int min, int max)
    {
        super(min, max);
        reset();
    }

    public JProgBar(int orient, int min, int max)
    {
        super(orient, min, max);
        reset();
    }
    
    public void setValue(int val)
    {
        super.setValue(val);
        paintNow();
    }

    public void setString(String str)
    {
        super.setString(str);
        super.setStringPainted(true);
        paintNow();
    }
    
    public void reset()
    {
        super.setValue(0);
        super.setStringPainted(false);
        paintNow();
    }
    
    public void paintNow()
    {
        Dimension dim = getSize();
        paintImmediately(0, 0, dim.width, dim.width);
    }
}
