/*
 *  ProgNotify.java
 *
 *  Created on 6. Juni 2005, 21:03
 *
 *  Copyright (C) 6. Juni 2005  <Reiner>

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

package shared.proglistener;

/**
 *
 * @author reiner
 */
public class ProgNotify
{
    public static final int ABORT = -2;
    public static final int ERROR = -1;
    public static final int START = 0;
    public static final int RUN = 1;
    public static final int END = 2;

    private int m_reason;
    private int m_progress; // 0-1000
    private String m_msg;

    public int getReason()
	    {return m_reason;}
    public void setReason(int reason)
	    {m_reason = reason;}

    public int getProgress()
	    {return m_progress;}
    public void setProgress(int progress)
	    {m_progress = progress;}

    public String getMessage()
	    {return m_msg;}
    public void setMessage(String msg)
	    {m_msg = msg;}

    /** Creates a new instance of ProgNotify */
    public ProgNotify()
    {
        m_progress = 0;
        m_reason = ERROR;
        m_msg = null;
    }

    public ProgNotify(int reason)
    {
        m_reason = reason;
        m_progress = 0;
        m_msg = null;
    }

    public ProgNotify(int reason, int progress)
    {
        m_reason = reason;
        m_progress = progress;
        m_msg = null;
    }

    public ProgNotify(int reason, int progress, String msg)
    {
        m_reason = reason;
        m_progress = progress;
        m_msg = msg;
    }

    public ProgNotify(int reason, String msg)
    {
        m_reason = reason;
        m_progress = 0;
        m_msg = msg;
    }
}
