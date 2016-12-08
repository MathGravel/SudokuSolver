/*
 * JSleepHelper.java
 *
 * Created on 13. Dezember 2006, 23:02
 *
 *  Copyright (C) 13. Dezember 2006  <reiner>
 
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


package shared.misc;

/**
 *
 * @author reiner
 */

public class JSleepHelper
{
    
    /** Creates a new instance of JSleepHelper */
    public JSleepHelper()
    {
    }
    
    public static boolean sleep(int msec)
    {
        boolean flag = false;
        try
        {
            Thread.sleep(msec);
            flag = true;
        }
        catch(InterruptedException ex)
        {}
        return flag;
    }
}
