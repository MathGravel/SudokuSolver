/*
 *  ImageObserverAdapter.java
 *
 *  Created on 30. April 2006, 19:22
 *
 *  Copyright (C) 30. April 2006  <Reiner>

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


package shared.image;

import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author reiner
 */

public class ImageObserverAdapter implements ImageObserver
{
    
    private boolean m_bTrace = false;
    
    /** Creates a new instance of ImageObserverAdapter */
    public ImageObserverAdapter(boolean bTrace)
    {
        m_bTrace = bTrace;
    }

    public ImageObserverAdapter()
    {
    }
    
    public boolean  imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if (m_bTrace)
        {
            if ((infoflags & ImageObserver.WIDTH) != 0)
            System.err.print("Width: " + width + "\t");
            if ((infoflags & ImageObserver.HEIGHT) != 0)
            System.err.print("Height: " + height + "\t");
            if ((infoflags & ImageObserver.PROPERTIES) != 0)
            System.err.print("Properties \t");
            if ((infoflags & ImageObserver.SOMEBITS) != 0)
            System.err.print("Somebits: (" + x +", " + y+") ->(" + width + ", " + height + ")\t");
            if ((infoflags & ImageObserver.FRAMEBITS) != 0)
            System.err.print("Somebits: (" + x +", " + y+") ->(" + width + ", " + height + ")\t");
            if ((infoflags & ImageObserver.ALLBITS) != 0)
            System.err.print("Somebits: (" + x +", " + y+") ->(" + width + ", " + height + ")\t");
            if ((infoflags & ImageObserver.ABORT) != 0)
            System.err.print("Abort\t");
            if ((infoflags & ImageObserver.ERROR) != 0)
            System.err.print("Error\t");
            System.err.println();
        }
        boolean flag = true;
        if (((infoflags & ImageObserver.ABORT) != 0) || ((infoflags & ImageObserver.ERROR) != 0))
            flag = false;
        return true;
    }
}
