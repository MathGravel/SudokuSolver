/*
 *  JGridBagPanel.java
 *
 *  Created on 20. Februar 2005, 22:30
 *
 *  Copyright (C) 20. Februar 2005  <Reiner>

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


package shared.layout;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 *
 * @author Reiner
 */
public class JGridBagPanel extends JPanel
{
    /** Creates a new instance of JGridBagPanel */
    public JGridBagPanel()
    {
    	super();
        setLayout(new GridBagLayout());
    }
	
    /**
     * adds a component to the JGridBagPanel
     * @param component the component to be added
     * @param x the column
     * @param y the row
     * @param anchor the alignment
     */
    
    public void addComponent(Component component, int x, int y, int anchor)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = anchor;
        add(component, constraints);
    }

    /**
     * adds a component to the JGridBagPanel
     * @param component the component to be added
     * @param x the column
     * @param y the row
     * @param anchor the alignment
     * @param insets the insest
     */
    public void addComponent(Component component, int x, int y, int anchor, Insets insets)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = anchor;
        constraints.insets = insets;
        add(component, constraints);
    }

	public void addComponent(Component component, int x, int y, int anchor, Insets insets, int gridw, int gridh)
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = x;
	    constraints.gridy = y;
	    constraints.anchor = anchor;
	    constraints.insets = insets;
	    constraints.gridwidth = gridw;
	    constraints.gridheight = gridh;
	    add(component, constraints);
	}

	public void addComponent(Component component, int x, int y, int anchor, int gridw, int gridh)
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = x;
	    constraints.gridy = y;
	    constraints.anchor = anchor;
	    constraints.gridwidth = gridw;
	    constraints.gridheight = gridh;
	    add(component, constraints);
	}

	public void addComponent(Component component, int x, int y, int anchor, Insets insets, int gridw, int gridh, double weightx, double weighty)
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = x;
	    constraints.gridy = y;
	    constraints.anchor = anchor;
	    constraints.insets = insets;
	    constraints.gridwidth = gridw;
	    constraints.gridheight = gridh;
	    constraints.weightx = weightx;
	    constraints.weighty = weighty;
	    add(component, constraints);
	}

	public void addComponent(Component component, int x, int y, int anchor, Insets insets, int gridw, int gridh, double weightx, double weighty, int fill)
	{
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = x;
	    constraints.gridy = y;
	    constraints.anchor = anchor;
	    constraints.insets = insets;
	    constraints.gridwidth = gridw;
	    constraints.gridheight = gridh;
	    constraints.weightx = weightx;
	    constraints.weighty = weighty;
	    constraints.fill = fill;
	    add(component, constraints);
	}
}
