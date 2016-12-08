/** Created on 12.11.2008 14:38:48
 *
 * Copyright (C) 2008  PSYSTEME GmbH  Georg-Hallmaier-Str. 6  D-81369 Muenchen.
 *
 * @author reiner
 */

package shared.statusbar;

import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import shared.aa.JAALabel;

/**
 *
 */
public class JStatusbarDisplayField extends JAALabel
{
    private String m_text = "";
    
    public JStatusbarDisplayField()
    {
        doInit();
    }

    public JStatusbarDisplayField(String str)
    {
        m_text = str;
        doInit();
    }
    
    public void setText(String text)
    {
        m_text = text;
        doUpdateDisplay();
    }

    public String getText()
    {
        return m_text;
    }
    protected void doInit()
    {
        setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0), BorderFactory.createEtchedBorder()));
        setFont(getFont().deriveFont(11.0f));
        doUpdateDisplay();
    }
    
    protected void doUpdateDisplay()
    {
        super.setText(" " + m_text + " ");
    }
}
