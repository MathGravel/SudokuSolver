/*
 * JCheckListListener.java
 *
 * Created on 18. September 2007, 19:02
 *
 *  Copyright (C) 18. September 2007  <reiner>
 
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


package shared.checklist;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.UIManager;


/**
 *
 * @author reiner
 */

final class JCheckListListener extends MouseAdapter implements KeyListener
{
    protected JList m_list;
    protected int m_checkBoxWidth = 0;
    protected void doToggleCheck()
    {
	int index = m_list.getSelectedIndex();
	if (index >= 0)
	{
	    Object obj = m_list.getModel().getElementAt(index);
	    assert obj instanceof JCheckListData;
	    if (obj instanceof JCheckListData)
	    {
		JCheckListData data = (JCheckListData)obj;
		data.setChecked(!data.isChecked());
		m_list.repaint();
	    }
	}
    }
    
    public JCheckListListener(JList list)
    {
	super();
	m_list = list;
    }
    public void keyPressed(KeyEvent e)
    {
	if (e.getKeyChar() == ' ')
	    doToggleCheck();
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void  keyTyped(KeyEvent e)
    {
    }
    
    public void mouseClicked(MouseEvent ev)
    {
	if (m_checkBoxWidth == 0)
	    m_checkBoxWidth = ((Icon)UIManager.get("CheckBox.icon")).getIconWidth() + (Integer)UIManager.get("CheckBox.textIconGap")/2; 
	if (ev.getX() <=  m_checkBoxWidth)
	    doToggleCheck();
    }
}
