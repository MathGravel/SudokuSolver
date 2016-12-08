/*
 * JSettings.java
 *
 * Created on 26. Oktober 2007, 22:33
 *
 *  Copyright (C) 26. Oktober 2007  <reiner>
 
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


package jsudoku;

import java.awt.Color;

import java.util.Properties;

import shared.settings.JSettingsHelper;


/**
 *
 * @author reiner
 */

public final class JSettings extends JSettingsHelper
{
    
    public static final String SETTINGS_FILE = "jsudoku.properties";
    public static final String COMMENT = "JSudoku settings";

    private static final String DISPLAYGRIDLINECOLOR = "DisplayGridLineColor";
    private static final String DISPLAYINITTEXTCOLOR = "DisplayInitTextColor";
    private static final String DISPLAYUSERTEXTCOLOR = "DisplayUserTextColor";
    private static final String DISPLAYINVALIDTEXTCOLOR = "DisplayInValidTextColor";
    private static final String DISPLAYBACKCOLOR = "DisplayBackColor";

    private static final String PRINTGRIDLINECOLOR = "PrintGridLineColor";
    private static final String PRINTINITTEXTCOLOR = "PrintInitTextColor";
    private static final String PRINTUSERTEXTCOLOR = "PrintUserTextColor";
    private static final String PRINTINVALIDTEXTCOLOR = "PrintInValidTextColor";
    private static final String PRINTBACKCOLOR = "PrintBackColor";

    private static final String SUDOKUDIRECTORY = "Directory";
    private static final String SUDOKUMINFREEFIELDS = "MinFreeFields";
    private static final String SUDOKUMAXFREEFIELDS = "MaxFreeFields";

    private static final String DEF_DISPLAYGRIDLINECOLOR = "ff666666";
    private static final String DEF_DISPLAYINITTEXTCOLOR = "ff000000";
    private static final String DEF_DISPLAYUSERTEXTCOLOR = "ff0000ff";
    private static final String DEF_DISPLAYINVALIDTEXTCOLOR = "ffff0000";
    private static final String DEF_DISPLAYBACKCOLOR = "ffffffcc";

    private static final String DEF_PRINTGRIDLINECOLOR = "ff000000";
    private static final String DEF_PRINTINITTEXTCOLOR = "ff000000";
    private static final String DEF_PRINTUSERTEXTCOLOR = "ff0000ff";
    private static final String DEF_PRINTINVALIDTEXTCOLOR = "ffff0000";
    private static final String DEF_PRINTBACKCOLOR = "ffffffff";

    private static final String DEF_SUDOKUDIRECTORY = "";
    private static final String DEF_SUDOKUDFILEEXTENSION = "sdk";
    private static final String DEF_SUDOKUMINFREEFIELDS = "45";
    private static final String DEF_SUDOKUMAXFREEFIELDS = "60";

    private Color m_displayGridLineColor;
    private Color m_displayInitTextColor;
    private Color m_displayUserTextColor;
    private Color m_displayInvalidTextColor;
    private Color m_displayBackColor;

    private Color m_printGridLineColor;
    private Color m_printInitTextColor;
    private Color m_printUserTextColor;
    private Color m_printInvalidTextColor;
    private Color m_printBackColor;
    
    private String m_directory;
    private int m_minFreeFields;
    private int m_maxFreeFields;
    
    /** Creates a new instance of JSettings */
    
    public JSettings(boolean bInitSwing)
    {
    	super(bInitSwing);
    }
    
    public String getSudokuFileExtension()
    {
        return DEF_SUDOKUDFILEEXTENSION;
    }

    // Display
    public Color getDisplayGridLineColor()
    {
    	return m_displayGridLineColor;
    }

    public void setDisplayGridLineColor(Color displayGridLineColor)
    {
    	m_displayGridLineColor = displayGridLineColor;
    }
    
    public Color getDisplayInitTextColor()
    {
    	return m_displayInitTextColor;
    }

    public void setDisplayInitTextColor(Color displayInitTextColor)
    {
    	m_displayInitTextColor = displayInitTextColor;
    }
    
    public Color getDisplayUserTextColor()
    {
    	return m_displayUserTextColor;
    }

    public void setDisplayUserTextColor(Color displayUserTextColor)
    {
    	m_displayUserTextColor = displayUserTextColor;
    }
    
    public Color getDisplayInvalidTextColor()
    {
    	return m_displayInvalidTextColor;
    }

    public void setDisplayInvalidTextColor(Color displayInvalidTextColor)
    {
        m_displayInvalidTextColor = displayInvalidTextColor;
    }

    public Color getDisplayBackColor()
    {
        return m_displayBackColor;
    }

    public void setDisplayBackColor(Color displayBackColor)
    {
        m_displayBackColor = displayBackColor;
    }

    // Print
    public Color getPrintGridLineColor()
    {
        return m_printGridLineColor;
    }

    public void setPrintGridLineColor(Color printGridLineColor)
    {
        m_printGridLineColor = printGridLineColor;
    }
    
    public Color getPrintInitTextColor()
    {
        return m_printInitTextColor;
    }

    public void setPrintInitTextColor(Color printInitTextColor)
    {
        m_printInitTextColor = printInitTextColor;
    }
    
    public Color getPrintUserTextColor()
    {
    	return m_printUserTextColor;
    }

    public void setPrintUserTextColor(Color printUserTextColor)
    {
        m_printUserTextColor = printUserTextColor;
    }
    
    public Color getPrintInvalidTextColor()
    {
    	return m_printInvalidTextColor;
    }

    public void setPrintInvalidTextColor(Color printInvalidTextColor)
    {
        m_printInvalidTextColor = printInvalidTextColor;
    }

    public Color getPrintBackColor()
    {
    	return m_printBackColor;
    }

    public void setPrintBackColor(Color printBackColor)
    {
    	m_printBackColor = printBackColor;
    }

    public String getSudokuDirectory()
    {
        return m_directory;
    }

    public void setSudokuDirectory(String sudokuDirectory)
    {
    	m_directory = sudokuDirectory;
    }
    
    public int getMinFreeFields()
    {
    	return m_minFreeFields;
    }
    
    public void setMinFreeFields(int minFreeFields)
    {
    	m_minFreeFields = minFreeFields;
    }

    public int getMaxFreeFields()
    {
    	return m_maxFreeFields;
    }

    public void setMaxFreeFields(int maxFreeFields)
    {
    	m_maxFreeFields = maxFreeFields;
    }

    protected String getComment()
    {
    	return COMMENT;
    }
    
    protected void doLoad(Properties prop)
    {
        m_displayGridLineColor = new Color((int)Long.parseLong(prop.getProperty(DISPLAYGRIDLINECOLOR, DEF_DISPLAYGRIDLINECOLOR), 16));
        m_displayInitTextColor = new Color((int)Long.parseLong(prop.getProperty(DISPLAYINITTEXTCOLOR, DEF_DISPLAYINITTEXTCOLOR), 16));
        m_displayUserTextColor = new Color((int)Long.parseLong(prop.getProperty(DISPLAYUSERTEXTCOLOR, DEF_DISPLAYUSERTEXTCOLOR), 16));
        m_displayInvalidTextColor = new Color((int)Long.parseLong(prop.getProperty(DISPLAYINVALIDTEXTCOLOR, DEF_DISPLAYINVALIDTEXTCOLOR), 16));
        m_displayBackColor = new Color((int)Long.parseLong(prop.getProperty(DISPLAYBACKCOLOR, DEF_DISPLAYBACKCOLOR), 16));

            m_printGridLineColor = new Color((int)Long.parseLong(prop.getProperty(PRINTGRIDLINECOLOR, DEF_PRINTGRIDLINECOLOR), 16));
        m_printInitTextColor = new Color((int)Long.parseLong(prop.getProperty(PRINTINITTEXTCOLOR, DEF_PRINTINITTEXTCOLOR), 16));
        m_printUserTextColor = new Color((int)Long.parseLong(prop.getProperty(PRINTUSERTEXTCOLOR, DEF_PRINTUSERTEXTCOLOR), 16));
        m_printInvalidTextColor = new Color((int)Long.parseLong(prop.getProperty(PRINTINVALIDTEXTCOLOR, DEF_PRINTINVALIDTEXTCOLOR), 16));
        m_printBackColor = new Color((int)Long.parseLong(prop.getProperty(PRINTBACKCOLOR, DEF_PRINTBACKCOLOR), 16));

        m_directory = prop.getProperty(SUDOKUDIRECTORY, DEF_SUDOKUDIRECTORY);

        m_minFreeFields = new Integer(prop.getProperty(SUDOKUMINFREEFIELDS, DEF_SUDOKUMINFREEFIELDS));
        m_maxFreeFields = new Integer(prop.getProperty(SUDOKUMAXFREEFIELDS, DEF_SUDOKUMAXFREEFIELDS));
    }
    
    protected void doSave(Properties prop)
    {
        prop.setProperty(DISPLAYGRIDLINECOLOR, Integer.toHexString(m_displayGridLineColor.getRGB()));
        prop.setProperty(DISPLAYINITTEXTCOLOR, Integer.toHexString(m_displayInitTextColor.getRGB()));
        prop.setProperty(DISPLAYUSERTEXTCOLOR, Integer.toHexString(m_displayUserTextColor.getRGB()));
        prop.setProperty(DISPLAYINVALIDTEXTCOLOR, Integer.toHexString(m_displayInvalidTextColor.getRGB()));
        prop.setProperty(DISPLAYBACKCOLOR, Integer.toHexString(m_displayBackColor.getRGB()));

        prop.setProperty(PRINTGRIDLINECOLOR, Integer.toHexString(m_printGridLineColor.getRGB()));
        prop.setProperty(PRINTINITTEXTCOLOR, Integer.toHexString(m_printInitTextColor.getRGB()));
        prop.setProperty(PRINTUSERTEXTCOLOR, Integer.toHexString(m_printUserTextColor.getRGB()));
        prop.setProperty(PRINTINVALIDTEXTCOLOR, Integer.toHexString(m_printInvalidTextColor.getRGB()));
        prop.setProperty(PRINTBACKCOLOR, Integer.toHexString(m_printBackColor.getRGB()));

        prop.setProperty(SUDOKUDIRECTORY, m_directory);

        prop.setProperty(SUDOKUMINFREEFIELDS, Integer.toString(m_minFreeFields));
        prop.setProperty(SUDOKUMAXFREEFIELDS, Integer.toString(m_maxFreeFields));
    }
}
