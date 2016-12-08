/*
 * JSwingHelper.java
 *
 * Created on 6. Mai 2006, 15:45
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


package shared.swing;

import javax.swing.plaf.FontUIResource;
import javax.swing.UIManager;
import java.util.Enumeration;

/**
 *
 * @author reiner
 */

public class JSwingHelper
{
    /** Creates a new instance of JSwingHelper */
    public JSwingHelper()
    {
    }
    
    public static void unBoldAllFonts()
    {
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        /*
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
            {
            FontUIResource fr = (javax.swing.plaf.FontUIResource)value;
            if (!key.toString().equals("InternalFrame.titleFont") && fr.isBold())
                UIManager.put(key, new FontUIResource(fr.deriveFont(java.awt.Font.PLAIN)));
            }
        }*/
	
    }
    
    /**
     * switchs on anti aliasing
     * does only work on 1.5 and must there the first swing call !!!
     * seems not to be necessarry with JDK 1.6
     */
    public static void setAnitAliasing()
    {
        System.setProperty("swing.aatext", "true");
    }
}
