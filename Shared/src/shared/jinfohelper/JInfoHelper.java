/*
 *  JInfoHelper.java
 *
 *  Created on 29. April 2005, 22:42
 *
 *  Copyright (C) 29. April 2005  <Reiner>

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

package shared.jinfohelper;

import java.util.Properties;
import java.util.Enumeration;

import shared.files.JPathHelper;

/**
 *
 * @author reiner
 */
public class JInfoHelper
{
  
    /** Creates a new instance of JInfoHelper */
    public JInfoHelper()
    {
    }
    
    /**
     * returns in string with the java version number ander other java details
     * @param details 0 = basic; 1 = more; 2=more more, 3 = all
     */
    public static String getJavaInfo(int details)
    {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Java version: ");
        strBuilder.append(System.getProperty("java.version"));

        strBuilder.append("\nJava vendor: ");
        strBuilder.append(System.getProperty("java.vendor"));

        if (details >= 1)
        {
            strBuilder.append("\nSystem: ");
            strBuilder.append(System.getProperty("os.name"));
            strBuilder.append(" ");
            strBuilder.append(System.getProperty("os.version"));
        }
        if (details >= 2)
        {
            strBuilder.append("\nJava.home: ");
            strBuilder.append(System.getProperty("java.home"));

            strBuilder.append("\nClasspath: ");
            String[] strArray = System.getProperty("java.class.path").split("\\" + System.getProperty("path.separator"));
            for (String str : strArray)
            strBuilder.append("\n" + str);
        }
        if (details >= 3)
        {
            Properties prop = System.getProperties();
            Enumeration keyEnum = prop.propertyNames();
            while (keyEnum.hasMoreElements())
            {
                String key = (String)keyEnum.nextElement();
                strBuilder.append("\n");
                strBuilder.append(key);
                strBuilder.append(": ");
                strBuilder.append(prop.getProperty(key));
            }
        }
        return strBuilder.toString();
    }
    
    /**
     * returns the path to the java executable
     * @return  path to java (java.exe)
    */
    
    public static String getJavaExecutablePath()
    {
	String str = System.getProperty("java.home");
	if (str != null)
	{
	    StringBuilder strBuilder = new StringBuilder(str);
	    JPathHelper.addSeparator(strBuilder);
	    strBuilder.append("bin");
	    JPathHelper.addSeparator(strBuilder);
	    strBuilder.append("java");
	    str = strBuilder.toString();
	}
	else str = "java"; // we hope that there will be a $PATH entry to find it !
	return str;
    }
}
