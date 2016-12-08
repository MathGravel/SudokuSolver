/*
 *  JNumberSpinner.java
 *
 *  Created on 28. April 2006, 13:59
 *
 *  Copyright (C) 28. April 2006  <Reiner>

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

package shared.numberspinner;

/**
 *
 * @author reiner
 */

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class JNumberSpinner extends JSpinner
{
    
    /** Creates a new instance of JNumberSpinner */
    public JNumberSpinner(SpinnerModel model)
    {
        super(model);
    }

    protected JComponent createEditor(SpinnerModel model)
    {
        if (model instanceof SpinnerNumberModel)
        {
            JSpinner.NumberEditor ne = new JSpinner.NumberEditor(this, "#");
            return ne;
        }
        else
        {
            assert false;
            return new DefaultEditor(this);
        }
    }
}
