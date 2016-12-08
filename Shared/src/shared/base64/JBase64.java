/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package shared.base64;

/**
 * JBase64
 *
 * Created on 02.02.2008, 18:46:12
 *
 *  Copyright (C) 02.02.2008  <reiner>
 *
 * @author reiner
 */

import java.io.UnsupportedEncodingException;

/**
 * @see <a href="http://www.faqs.org/rfcs/rfc3548.html">http://www.faqs.org/rfcs/rfc3548.html</a>
 */

public class JBase64
{
    private static final char[] m_b64Tbl=
    {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '='
    };
    
    public JBase64()
    {
    }
    
    public static String encode(String str)
    {
    	return encode(str, "ISO-8859-1");
    }
    
    public static String encode(String str, String charSet)
    {
        String b64Str = null;
        try
        {
            b64Str = encode(str.getBytes(charSet));
        }
        catch(UnsupportedEncodingException ex)
        {
        }
        return b64Str;
    }
    
    public static String encode(byte[] buf)
    {
    	return encode(buf, 0, buf.length);
    }
    
    public static String encode(byte[] buf, int offset, int size)
    {
        StringBuffer strBuf = new StringBuffer();
        int i = offset;
        int val;
        while(size >= 3)
        {
            // 1. Char
            val = (buf[i] & 0xFC) >>> 2;
            strBuf.append(m_b64Tbl[val]);

            // 2. Char
            val = (buf[i++] & 0x03) << 4;
            val |= (buf[i] & 0xF0) >>> 4;
            strBuf.append(m_b64Tbl[val]);

            // 3. Char
            val = (buf[i++] & 0x0F) << 2;
            val |= (buf[i] & 0xC0) >>> 6;
            strBuf.append(m_b64Tbl[val]);

            // 4. Char
            val = (buf[i++] & 0x3F);
            strBuf.append(m_b64Tbl[val]);
            size -= 3;
        }
        if (size != 0)
        {
            if (size == 1)
            {
                // 1. Char
                val = (buf[i] & 0xFC) >>> 2;
                strBuf.append(m_b64Tbl[val]);

                // 2. Char
                val = (buf[i] & 0x03) << 4;
                strBuf.append(m_b64Tbl[val]);

                // 3. Char
                strBuf.append(m_b64Tbl[64]);

                // 4. Char
                strBuf.append(m_b64Tbl[64]);
            }
            else if (size == 2)
            {
                // 1. Char
                val = (buf[i] & 0xFC) >>> 2;
                strBuf.append(m_b64Tbl[val]);

                // 2. Char
                val = (buf[i++] & 0x03) << 4;
                val |= (buf[i] & 0xF0) >>> 4;
                strBuf.append(m_b64Tbl[val]);

                // 3. Char
                val = (buf[i] & 0x0F) << 2;
                strBuf.append(m_b64Tbl[val]);

                // 4. Char
                strBuf.append(m_b64Tbl[64]);
            }
        }
        return strBuf.toString();
    }
    
    public static byte[] decode(String str)
    {
        byte[] buf = null;
        int size = getDataSize(str);
        if (size > 0)
        {
            buf = new byte[size];
            int val;
            int i = 0, j = 0;
            size = str.length() - ((str.length()/4)*3 - size);
            while(size >= 4)
            {
                if ((val = getVal(str.charAt(i++))) != -1)
                {
                    buf[j] |= (val << 2);
                    if ((val = getVal(str.charAt(i++))) != -1)
                    {
                    buf[j++] |= ((val & 0x30) >>> 4);

                    // 2. byte
                    buf[j] |= (val & 0x0F) << 4;
                    if ((val = getVal(str.charAt(i++))) != -1)
                    {
                        buf[j++] |= ((val & 0x3C) >>> 2);

                        // 3. Byte
                        buf[j] |= (val & 0x03) << 6;
                        if ((val = getVal(str.charAt(i++))) != -1)
                        {
                        buf[j++] |= (val & 0x3F);
                        size -= 4;
                        }
                        else break;
                    }
                    else break;
                    }
                    else break;
                }
                else break;
            }	    

            if (size == 3)
            {
                if ((val = getVal(str.charAt(i++))) != -1)
                {
                    buf[j] |= (val << 2);
                    if ((val = getVal(str.charAt(i++))) != -1)
                    {
                        buf[j++] |= ((val & 0x30) >>> 4);

                        // 2. byte
                        buf[j] |= (val & 0x0F) << 4;
                        if ((val = getVal(str.charAt(i++))) != -1)
                        {
                            buf[j] |= ((val & 0x3C) >>> 2);
                            size -= 3;
                        }
                    }
                }
            }
            else if (size == 2)
            {
                if ((val = getVal(str.charAt(i++))) != -1)
                {
                    buf[j] |= (val << 2);
                    if ((val = getVal(str.charAt(i++))) != -1)
                    {
                        buf[j] |= ((val & 0x30) >>> 4);
                        size -= 2;
                    }
                }
            }
        }
        return size==0 ? buf : null;
    }

    private static int getVal(char c)
    {
        for (int i=0; i<64; i++)
        {
            if (m_b64Tbl[i] == c)
            return i;
        }
        return -1;
    }

    public static int getDataSize(String str)
    {
        int size = 0;
        assert str.length()%4 == 0;
        if (str.length() >= 4)
        {
            size = (str.length()/4)*3;
            if (str.charAt(str.length() - 1) == m_b64Tbl[64])
                size--;
            if (str.charAt(str.length() - 2) == m_b64Tbl[64])
            size--;
        }
        return size;
    }
}
