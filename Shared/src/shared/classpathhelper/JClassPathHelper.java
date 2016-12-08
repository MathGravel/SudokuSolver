/*
 *  JClassPathHelper.java
 *
 *  Created on 5. Mai 2005, 21:15
 *
 *  Copyright (C) 5. Mai 2005  <Reiner>

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


package shared.classpathhelper;

import java.net.URL;
import java.io.File;

/**
 *
 * @author reiner
 */
public class JClassPathHelper
{
	
    /** Creates a new instance of JClassPathHelper */
    public JClassPathHelper()
    {
    }
	/*
	public static String getClassPath(Object obj, boolean bOnlyPath)
	{
		String path = null;
		if (obj != null)
		{
			String className = obj.getClass().getName();
			int index = className.lastIndexOf(".");
			if (index >= 0)
				className = className.substring(index + 1, className.length());
			URL url = obj.getClass().getResource(className + ".class");
			if (url != null)
			{
				String str = url.toString();
				int endJar = str.indexOf("!/");
				if (endJar >= 0)
				{
					String prefix = "jar:file:/";
					String jarFile = str.substring(prefix.length(), endJar);
					if (bOnlyPath)
					{
						if ((index = jarFile.lastIndexOf('/')) >= 0)
							path = jarFile.substring(0, index);
						else path = jarFile; // ???
					}
					else path = jarFile;
				}
				else
				{
					String prefix = "file:/";
					String classFile = str.substring(prefix.length());
					if (bOnlyPath)
					{
						if ((index = classFile.lastIndexOf('/')) >= 0)
							path = classFile.substring(0, index);
						else path = classFile ; // ???
					}
					else path = classFile;
				}
			}
		}
		File file = new File(path);
		if (!file.isAbsolute())
			path = File.separatorChar + path;
		return path;
	}
	*/
	
    public static String getClassPath(Object obj, boolean bOnlyPath)
    {
        String path = null;
        if (obj != null)
        {
            String className = obj.getClass().getName();
            int index = className.lastIndexOf(".");
            if (index >= 0) className = className.substring(index + 1, className.length());
            URL url = obj.getClass().getResource(className + ".class");
            if (url != null)
            {
                try
                {
                    String str = url.toString();
                    int endJar = str.indexOf("!/");
                    if (endJar >= 0)
                    {
                        String prefix = "jar:";
                        url = new URL(str.substring(prefix.length(), endJar));
                    }
                    File file = new File(url.toURI());
                    str = file.getAbsolutePath();
                    if (bOnlyPath)
                    {
                        if ((index = str.lastIndexOf(File.separatorChar)) >= 0)
                        path = str.substring(0, index);
                        else path = str; // ???
                    }
                    else path = str;
                }
                catch(Exception ex)
                {
                }
            }
        }
        return path;
    }
}
