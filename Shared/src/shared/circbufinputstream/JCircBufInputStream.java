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

package shared.circbufinputstream;

import java.io.InputStream;
import java.io.IOException;

/**
 * JCircBufInputStream
 *
 * Created on 10.02.2008, 23:04:17
 *
 *  Copyright (C) 10.02.2008  <reiner>
 *
 * @author reiner
 */


public class JCircBufInputStream extends InputStream
{
    private byte [] m_buf;
    private int m_readPos;
    private int m_writePos;
    private int m_markPos;
    
    public JCircBufInputStream(int size)
    {
        m_buf = new byte[size];
        m_readPos = 0;
        m_writePos = 0;
        m_markPos = -1;
    }
    
    @Override
    public int available() throws IOException
    {
        if (m_buf == null) throw new IOException("Buffer is closed!");
        if (m_writePos >= m_readPos) return m_writePos - m_readPos;
        else return m_buf.length - m_readPos + m_writePos;
    }

    @Override
    public void close()
    {
    	m_buf = null;
    }
    
    @Override
    public void mark(int readlimit)
    {
    }
    
    @Override
    public boolean markSupported() 
    {
        return false;
    }
    
    @Override
    public int read() throws IOException
    {
        byte[] b = new byte[0];
        read(b, 0, 1);
        return b[0] & 0xFF;
    }
    
    @Override
    public int read(byte[] b) throws IOException
    {
    	return read(b, 0, b.length);
    }
    
    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
        if (m_buf == null) throw new IOException("Buffer is closed!");
        if (b == null) throw new NullPointerException("Invalid output buffer");
        if (off < -1 || len < 0 || len > b.length - off) throw new IndexOutOfBoundsException("negative skip values are not supported");
        int size = -1;
        if (m_readPos <= m_writePos)
        {
            size = Math.min(len, m_writePos - m_readPos);
            System.arraycopy(m_buf, m_readPos, b, off, size);
            m_readPos += size;
        }
        else
        {
            size = Math.min(m_buf.length - m_readPos + m_writePos, len);
            if (size <= m_buf.length - m_readPos)
            {
                System.arraycopy(m_buf, m_readPos, b, off, size);
                m_readPos += size;
            }
            else
            {
                System.arraycopy(m_buf, m_readPos, b, off, m_buf.length - m_readPos);
                System.arraycopy(m_buf, 0, b, off + m_buf.length - m_readPos, size - (m_buf.length - m_readPos));
                m_readPos = size - (m_buf.length - m_readPos);
            }
        }
        if (m_readPos == m_buf.length)
            m_readPos = 0;
        return size;
    }
    
    @Override
    public void reset() 
    {
    	m_markPos = -1;
    }
    
    @Override
    public long skip(long n)
    {
        if (n < 0) throw new IndexOutOfBoundsException("negative skip values are not supported");
        long skip = n;
        if (m_readPos <= m_writePos)
        {
            if (m_readPos + n <= m_writePos)
            m_readPos += n;
            else
            {
                skip = m_writePos - m_readPos;
                m_readPos = m_writePos;
            }
        }
        else
        {
            if (n <= m_buf.length - m_readPos + m_writePos)
            m_readPos = (int)n - (m_buf.length - m_readPos);
            else
            {
                skip = m_buf.length - m_readPos + m_writePos;
                m_readPos = m_writePos;
            }
        }
        return skip;
    }
    
    public void write(byte[] buf, int offset, int size)  throws IOException
    {
        if (m_buf == null) throw new IOException("Buffer is closed!");
        if (buf == null) throw new NullPointerException("Invalid input buffer");
        if (offset < 0 || size < 0 || offset + size > buf.length) throw new IndexOutOfBoundsException("Invalid arguments");
        if (m_readPos <= m_writePos)
        {
            size = Math.min(size, m_buf.length - m_writePos + m_readPos);
            if (size <= m_buf.length - m_writePos)
            {
                System.arraycopy(buf, offset, m_buf, m_writePos, size);
                m_writePos += size;
            }
            else
            {
                System.arraycopy(buf, offset, m_buf, m_writePos, m_buf.length - m_writePos);
                System.arraycopy(buf, offset + m_buf.length - m_writePos, m_buf, 0, size - (m_buf.length - m_writePos));
                m_writePos = size - (m_buf.length - m_writePos);
            }
        }
        if (m_writePos == m_buf.length)
            m_writePos = 0;
    }
}
