/*
 *  JMD5Helper.java
 *
 *  Created on 9. Mai 2005, 22:30
 *
 *  Copyright (C) 9. Mai 2005  <Reiner>

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


package shared.md5;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.security.MessageDigest;
		
import shared.proglistener.ProgListener;
import shared.proglistener.ProgNotify;

/**
 *
 * @author reiner
 */
public class JMD5Helper
{
	private static final int BUF_SIZE = 4096;
	private byte [] m_byteArray;

	
	/** Creates a new instance of JMD5Helper */
	public JMD5Helper()
	{
	}
	
	public byte[] getMD5()
	{
		return m_byteArray;
	}

	public String getMD5AsString()
	{
		String str = null;
		if (m_byteArray != null && m_byteArray.length == 16)
		{
			str = String.format("%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x%02x", m_byteArray[0], m_byteArray[1], m_byteArray[2], m_byteArray[3], m_byteArray[4], m_byteArray[5], m_byteArray[6], m_byteArray[7], m_byteArray[8], m_byteArray[9], m_byteArray[10], m_byteArray[11], m_byteArray[12], m_byteArray[13], m_byteArray[14], m_byteArray[15]);
		}
		return str;
	}
	
	protected void doCalculateMD5(File file, ProgListener listener)
	{
		FileInputStream inStream = null;
		try
		{
			inStream = new FileInputStream(file);
			long size = file.length();
			MessageDigest mg = MessageDigest.getInstance("MD5");
			byte [] buf = new byte[BUF_SIZE];
			int readed = -1;
			if (listener != null) listener.notify(new ProgNotify(ProgNotify.START, 0, new Long(size).toString()));
			long total = size;
			boolean bAbort = false;
			while(size != 0)
			{
				if ((readed = inStream.read(buf)) >= 0)
				{
					mg.update(buf, 0, readed);
					assert size <= readed;
					size -= readed;
					if (listener != null)
					{
						if (bAbort = listener.notify(new ProgNotify(ProgNotify.RUN, (int)(1000L - (size*1000L)/total), null)))
							break;
					}
				}
				else break;
			}
			if (bAbort)
			{
				if (listener != null) listener.notify(new ProgNotify(ProgNotify.ABORT, 0, null));
			}
			else
			{
				m_byteArray = mg.digest();
				String str = String.format("%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X", m_byteArray[0], m_byteArray[1], m_byteArray[2], m_byteArray[3], m_byteArray[4], m_byteArray[5], m_byteArray[6], m_byteArray[7], m_byteArray[8], m_byteArray[9], m_byteArray[10], m_byteArray[11], m_byteArray[12], m_byteArray[13], m_byteArray[14], m_byteArray[15]);
				if (listener != null) listener.notify(new ProgNotify(ProgNotify.END, 1000, str));
			}
		}
		catch (FileNotFoundException ex)
		{
			if (listener != null) listener.notify(new ProgNotify(ProgNotify.ERROR, 0, ex.toString()));
		}
		catch (Exception ex)
		{
			if (listener != null) listener.notify(new ProgNotify(ProgNotify.ERROR, 0, ex.toString()));
		}
		finally
		{
			try
			{
				if (inStream != null) inStream.close();
			}
			catch(IOException ex)
			{
			}
		}
	}
	
	public void calculateMD5(File file, ProgListener listener)
	{
		final File file_ = file;
		final ProgListener listener_ = listener;
		Thread thread = new Thread()
		{
			public void run()
			{
				doCalculateMD5(file_, listener_);
			}
		};
		thread.start();
	}

	public void calculateMD5(String fileName, ProgListener listener)
	{
		calculateMD5(new File(fileName), listener);
	}
}
