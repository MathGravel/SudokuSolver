/*
 *  JChecksumHelper.java
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


package shared.checksumhelper;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.security.MessageDigest;

import java.util.zip.CRC32;
import java.util.zip.Adler32;
		
import shared.proglistener.ProgListener;
import shared.proglistener.ProgNotify;


/**
 *
 * @author reiner
 */
public class JChecksumHelper
{
	private static final int BUF_SIZE = 4096;
	private byte [] m_byteArray;

	
	/** Creates a new instance of JMD5Helper */
	public JChecksumHelper()
	{
	}
	
	public byte[] getChecksum()
	{
		return m_byteArray;
	}

	public String getChecksumAsString()
	{
		int index = 0;
		StringBuilder str = new StringBuilder();
		while (index < m_byteArray.length)
		{
			str.append(String.format("%02x", m_byteArray[index++]));
		}
		return str.toString();
	}
	
	protected void doCalculateMD(File file, ProgListener listener, String algo)
	{
		FileInputStream inStream = null;
		try
		{
			inStream = new FileInputStream(file);
			long size = file.length();
			CRC32 crc32 = null;
			MessageDigest md = null;
			Adler32 adler32 = null;
			if (algo.equalsIgnoreCase("CRC32"))
				crc32 = new CRC32();
			else if (algo.equalsIgnoreCase("Adler32"))
				adler32 = new Adler32();
			else md = MessageDigest.getInstance(algo);
			byte [] buf = new byte[BUF_SIZE];
			int readed = -1;
			if (listener != null) listener.notify(new ProgNotify(ProgNotify.START, 0, new Long(size).toString()));
			long total = size;
			boolean bAbort = false;
			while(size != 0)
			{
				if ((readed = inStream.read(buf)) >= 0)
				{
					if (crc32 != null) crc32.update(buf, 0, readed);
					else if (adler32 != null)  adler32.update(buf, 0, readed);
					else md.update(buf, 0, readed);
					
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
				if (crc32 != null) m_byteArray = longToByteArray4(crc32.getValue());
				else if (adler32 != null) m_byteArray = longToByteArray4(adler32.getValue());
				else m_byteArray = md.digest();
			
				int index = 0;
				StringBuilder str = new StringBuilder();
				String temp = null;
				while (index < m_byteArray.length)
				{
					temp = String.format("%02x", m_byteArray[index++]);
					str.append(temp);
				}
				if (listener != null) listener.notify(new ProgNotify(ProgNotify.END, 1000, str.toString()));
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
	
	public byte[] longToByteArray4(long l)
	{
		byte[] byteArray = new byte[4];
		byteArray[3] = (byte)(l & 0xFF);
		byteArray[2] = (byte)((l>>8) & 0xFF);
		byteArray[1] = (byte)((l>>16) & 0xFF);
		byteArray[0] = (byte)((l>>24) & 0xFF);
		return byteArray;
	}
	
	public void calculateChecksum(File file, ProgListener listener, String algo)
	{
		final File file_ = file;
		final ProgListener listener_ = listener;
		final String algo_ = algo;
		Thread thread = new Thread()
		{
			public void run()
			{
				doCalculateMD(file_, listener_, algo_);
			}
		};
		thread.start();
	}

	public void calculateChecksum(String fileName, ProgListener listener, String algo)
	{
		calculateChecksum(new File(fileName), listener, algo);
	}
}
