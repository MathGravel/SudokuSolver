/*
 * JCsvHelper.java
 *
 * Created on 06.11.2008 14:22:10
 *
 *  Copyright (C) <reiner>
 
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

 * @author reiner
 */

package shared.csv;

import java.util.ArrayList;

/**
 *
 */
public class JCsvHelper
{
    public JCsvHelper()
    {
    }
    /**
     * Test
     * "Test",1234,"1,234","abc",xyz,a""bc"
     * @param str
     * @param fieldSep
     * @param textSep
     * @return
     */
    public static String[] parseLine(String str, char fieldSep, char textSep)
    {
        char c;
        int index = 0;
        ArrayList<String> strArray = new ArrayList<String>();
        boolean bInText = false;
        StringBuilder strBuilder = new StringBuilder();
        while(index < str.length())
        {
            c = str.charAt(index);
            if (c == textSep)
            {
                if (bInText)
                {
                    if (index+1 < str.length() && str.charAt(index+1) != textSep)
                        bInText = false;
                    else
                    {
                        strBuilder.append(c);
                        index++;
                    }
                }
                else
                    bInText = true;
            }
            else
            {
                if (bInText)
                    strBuilder.append(c);
                else
                {
                    if (c == fieldSep)
                    {
                        strArray.add(strBuilder.toString());
                        strBuilder = new StringBuilder();
                    }
                    else
                        strBuilder.append(c);
                }
            }
            index++;
        }
        strArray.add(strBuilder.toString());
        String [] array = new String[strArray.size()];
        index = 0;
        for (String item : strArray)
        {
            array[index] = strArray.get(index);
            index++;
        }
        return array;
    }
}
