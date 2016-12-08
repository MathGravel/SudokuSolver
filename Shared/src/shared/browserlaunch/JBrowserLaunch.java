/*
 * JBrowserLaunch.java
 *
 * Created on 11. November 2006, 22:42
 *
 *  Copyright (C) 11. November 2006  <reiner>
 
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


package shared.browserlaunch;

import java.lang.reflect.Method;

/**
 *
 * @author reiner
 * based on
 * @see <a href="http://www.centerkey.com/java/browser/">Bare Bones Browser Launch for Java</a>
 */

public class JBrowserLaunch
{
    
    /** Creates a new instance of JBrowserLaunch */
    public JBrowserLaunch()
    {
    }
    
    @SuppressWarnings("unchecked")
    public static boolean openURL(String url)
    {
        boolean flag = false;
        String osName = System.getProperty("os.name");
        try
        {
            if (osName.startsWith("Mac OS"))
            {
                // I can not test this !
                Class fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
                openURL.invoke(null, new Object[] {url});
                flag = true;
            }
            else if (osName.startsWith("Windows"))
            {
                Runtime.getRuntime().exec(new String[] {"explorer.exe", url});
                flag = true;
            }
            else
            {	//assume Unix or Linux
                String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
                String browser = null;
                for (String item : browsers)
                {
                    if (Runtime.getRuntime().exec(new String[] {"which", item}).waitFor() == 0)
                    browser = item;
                }
                if (browser != null)
                {
                    Runtime.getRuntime().exec(new String[] {browser, url});
                    flag = true;
                }
            }
        }
        catch(Exception ex)
        {
            flag = false;
        }
        return flag;
    }
}
