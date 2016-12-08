/*
 * JModalDialog.java
 *
 * Created on 06.11.2008 17:02:12
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


package shared.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 */
public abstract class JModalDialog extends JDialog
{
    private boolean m_bOk = false;
    private JPanel m_okCancelPanel;
    private JFrame m_frame;
    
    public JModalDialog(JFrame frame, String caption, String okStr, String cancelStr)
    {
        super(frame, caption, true);
        m_frame = frame;
		Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        m_okCancelPanel = new JPanel();
      
        m_okCancelPanel.setLayout(new BoxLayout(m_okCancelPanel, BoxLayout.X_AXIS));
        m_okCancelPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        m_okCancelPanel.add(Box.createHorizontalGlue());
        JButton ok = new JButton(okStr);
        ok.addActionListener(new ActionListener()
        {
    	    public void actionPerformed(ActionEvent ev)
            {
                m_bOk = true;
				doGetData();
                dispose();
            }
        });
        Dimension dim = ok.getPreferredSize();
        ok.setPreferredSize(new Dimension(100, (int)dim.getHeight()));
        m_okCancelPanel.add(ok);
        m_okCancelPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        JButton cancel = new JButton(cancelStr);
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                dispose();
            }
        });
        cancel.setPreferredSize(ok.getPreferredSize());
        m_okCancelPanel.add(cancel);

        contentPane.add(m_okCancelPanel, BorderLayout.SOUTH);
    }
    
    public void addCtrlPanel(JPanel panel)
    {
		Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);
        
        if (m_frame != null)
            setLocationRelativeTo(m_frame);
        pack();
    }
    
    public boolean doModal()
    {
        doInit();
		setVisible(true);
        return m_bOk;
    }
	
	protected abstract void doGetData();
	protected abstract void doInit();
}
