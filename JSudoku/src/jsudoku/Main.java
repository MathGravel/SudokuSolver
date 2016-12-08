/*
 * Main.java
 *
 * Created on 14. Oktober 2007, 22:10
 *
 *  Copyright (C) 14. Oktober 2007  <reiner>
 
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

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import shared.files.JPathHelper;

/**
 *
 * @author reiner
 */
public class Main
{
    
    public static final String m_version = "1.1";
    public final static String m_versionString = "Author: Reiner Pr\u00F6ls\nVersion: " + m_version + "\nCopyright \u00A9 2007-2008 by Reiner Pr\u00F6ls\nLicense: GPL";

    public static JMainFrame m_mainFrame;
    public static JSettings m_settings;
    public static ImageIcon m_icon16;
    public static ImageIcon m_icon32;
    public static Locale m_sysLocale;

    /** Creates a new instance of Main */
    public Main()
    {
    }
    
    public static String getMessageBoxCaption()
    {
        String str = null;
        try
        {
            str = m_settings.getResBundle().getString("caption");
        }
        catch(Exception ex)
        {
        }
        if (str == null) str = new String("JSudoku");
        return str;
    }
    
    public static String getString(String key, boolean bReplaceLFTAB)
    {
    	return m_settings.getResBundleString(key, bReplaceLFTAB);
    }
    
    public static String getString(String key)
    {
        return getString(key, true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final String arguments[] = args.clone();
        // Load settings
        m_settings = new JSettings(true);

        m_icon16 = new ImageIcon(Main.class.getResource("icon16x16.png"));
        m_icon32 = new ImageIcon(Main.class.getResource("icon32x32.png"));
        m_settings.load(JPathHelper.addSeparator(System.getProperty("user.home")) + m_settings.SETTINGS_FILE, m_version);
        m_sysLocale = Locale.getDefault();
        if (m_settings.getLocale().length() > 0)
            Locale.setDefault(new Locale(m_settings.getLocale()));
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                initGui(arguments);
            }
        });
    }
    
    private static void initGui(String[] args)
    {
        if (m_settings.isDebug())
        {
            Properties props = System.getProperties();
            Enumeration enumProp = props.propertyNames();
            while (enumProp.hasMoreElements())
            {
            String key = (String)enumProp.nextElement();
            String val = System.getProperty(key);
            System.err.println(key + ": " + val);
            }
        }
        if (!m_settings.init("JSudokuLang", "JSudoku"))
            System.exit(1);

        m_mainFrame = new JMainFrame();
        m_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m_mainFrame.setVisible(true);
    }
}
