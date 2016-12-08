/*
 * JSettingsHelper.java
 *
 * Created on 17. November 2006, 20:04
 *
 *  Copyright (C) 17. November 2006  <reiner>
 
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


package shared.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import shared.swing.JSwingHelper;

/**
 * Base class for settings. At the moment the following entries are available
 * <ul>
 * <li><code>Debug</code></li>
 * <li><code>NoBoldFont</code></li>
 * <li><code>Locale</code></li>
 * <li><code>LookAndFeel</code></li>
 * <li><code>ResBundleFile</code></li>
 * </ul>
 * @author Reiner Prls
 */

abstract public class JSettingsHelper
{
    
    private static final String DEBUG = "Debug";
    private static final String NOBOLDFONT = "NoBoldFont";
    private static final String LOCALE = "Locale";
    private static final String LOOKANDFEEL = "LookAndFeel";
    private static final String RESBUNDLEFILE = "ResBundleFile";
    private static final String CHECKUPDATE = "CheckUpdates";
    private static final String VERSION = "Version";

    private static final String DEF_DEBUG = "false";
    private static final String DEF_NOBOLDFONT = "true";
    private static final String DEF_LOCALE = "";
    private static final String DEF_CHECKUPDATE = "true";
    private static final String DEF_VERSION = "";

    protected boolean m_bDebug;
    protected boolean m_bNoBoldFont;
    protected String m_locale;
    protected String m_lookAndFeel;
    protected String m_resBundleFile;
    protected String m_version;
    protected boolean m_bInitSwing;
    protected boolean m_bCheckUpdate;
    
    protected ResourceBundle m_resBundle;

    /** Creates a new instance of JSettingsHelper
     * @param bInitSwing if true some Swing initialization is done
    */
    public JSettingsHelper(boolean bInitSwing)
    {
        if (m_bInitSwing = bInitSwing)
            JSwingHelper.setAnitAliasing();
    }
    
    /**
     *	this functions must be implemented in derived classes
     */
    abstract protected void doLoad(Properties prop);
    abstract protected void doSave(Properties prop);
    abstract protected String getComment();

    private void createDummy(String fileName)
    {
        Properties prop = new Properties();
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(fileName);
            prop.store(out, getComment());
        }
        catch (IOException ex)
        {
        }
        finally
        {
            try
            {
                if (out != null) out.close();
            }
            catch (IOException ex)
            {}
        }
    }
    
    public void load(String fileName, String version)
    {
        FileInputStream in = null;
        File file = new File(fileName);
        if (!file.exists()) createDummy(fileName);
        if (file.canRead())
        {
            try
            {
                in = new FileInputStream(file);
                Properties prop = new Properties();
                prop.load(in);

                m_bDebug = new Boolean(prop.getProperty(DEBUG, DEF_DEBUG));
                m_bNoBoldFont = new Boolean(prop.getProperty(NOBOLDFONT, DEF_NOBOLDFONT));
                m_locale = prop.getProperty(LOCALE, DEF_LOCALE);
                m_lookAndFeel = prop.getProperty(LOOKANDFEEL, UIManager.getCrossPlatformLookAndFeelClassName());
                m_resBundleFile = prop.getProperty(RESBUNDLEFILE, "");
                m_bCheckUpdate = new Boolean(prop.getProperty(CHECKUPDATE, DEF_CHECKUPDATE));
                m_version = prop.getProperty(VERSION, DEF_VERSION);

                if (getLocale().length() > 0)
                    Locale.setDefault(new Locale(getLocale()));

                if (m_bInitSwing)
                {
                    if (isNoBoldFont())
                    JSwingHelper.unBoldAllFonts();
                }

                doLoad(prop);
                m_version = version;
            }
            catch(IOException ex)
            {}
            finally
            {
                try
                {
                    if (in != null) in.close();
                }
                catch (IOException ex)
                {}
            }
        }
    }
    
    public void save(String fileName)
    {
        Properties prop = new Properties();

        prop.setProperty(DEBUG, Boolean.toString(m_bDebug));
        prop.setProperty(NOBOLDFONT, Boolean.toString(m_bNoBoldFont));
        prop.setProperty(LOCALE, m_locale);
        prop.setProperty(LOOKANDFEEL, m_lookAndFeel);
        prop.setProperty(RESBUNDLEFILE, m_resBundleFile);
        prop.setProperty(CHECKUPDATE, Boolean.toString(m_bCheckUpdate));
        prop.setProperty(VERSION, m_version);

        doSave(prop);

        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(fileName);
            prop.store(out, getComment());
        }
        catch (IOException ex)
        {
        }
        finally
        {
            try
            {
                if (out != null) out.close();
            }
            catch (IOException ex)
            {}
        }
    }
    
    public ResourceBundle getResBundle()
    {
        return m_resBundle;
    }

    public String getResBundleString(String key)
    {
        return m_resBundle.getString(key);
    }

    /**
     *	this functions must be implemented in derived classes
     * @param bReplaceLFTAB if true literal \n and \t are replaced with the ASCII codes (0x0A and 0x09)
     */
    public String getResBundleString(String key, boolean bReplaceLFTAB)
    {
        String str = getResBundleString(key);
        if (bReplaceLFTAB)
        {
            str.replace("\\n", "\n");
            str.replace("\\t", "\t");
        }
        return str;
    }

    public void setIsDebug(boolean bDebug)
    {
        m_bDebug = bDebug;
    }
    
    public boolean isDebug()
    {
        return m_bDebug;
    }
    
    public boolean isNoBoldFont()
    {
        return m_bNoBoldFont;
    }
    
    public String getLocale()
    {
        return m_locale;
    }

    public void setLocale(String locale)
    {
        m_locale = locale;
    }
 
    public void setLookAndFeel(String lookAndFeel)
    {
        m_lookAndFeel = lookAndFeel;
    }

    public String getLookAndFeel()
    {
        return m_lookAndFeel;
    }

    public boolean isCheckUpdate()
    {
        return m_bCheckUpdate;
    }

    public void setCheckUpdate(boolean bCheckUpdate)
    {
        m_bCheckUpdate = bCheckUpdate;
    }

    public String getResBundleFile()
    {
        return m_resBundleFile;
    }
    
    /*
     * Call this function in your initGui function before any gui is created!
     */
    
    public boolean init(String resBundleName)
    {
        return init(resBundleName, null, null);
    }

    public boolean init(String resBundleName, String errMsgBoxCaption)
    {
        return init(resBundleName, errMsgBoxCaption, null);
    }
    
    public boolean init(String resBundleName, String errMsgBoxCaption, String errMsgBoxMessage)
    {
        if (m_bInitSwing)
        {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }
        String str = null;
        if (m_resBundleFile != null)
            str = getResBundleFile().trim();
        try
        {
            if (str != null && str.length() > 0)
            {
                final String resBundleFile = str + ".properties";
                m_resBundle = ResourceBundle.getBundle(str, Locale.getDefault(), new ClassLoader()
                {
                    public InputStream getResourceAsStream(String name)
                    {
                    if (name.equals(resBundleFile))
                    {
                        try
                        {
                            return new FileInputStream(name);
                        }
                        catch(FileNotFoundException ex)
                        {}
                    }
                    return null;
                    }
                });
            }
            else m_resBundle = ResourceBundle.getBundle(resBundleName);
        }
        catch (MissingResourceException ex)
        {
            // Message text must be hard codedd !!! No language file was found!
            JOptionPane.showMessageDialog(null, errMsgBoxMessage!= null ?  errMsgBoxMessage : "No resource file was found!\nThe program will terminate now!", errMsgBoxCaption != null ? errMsgBoxCaption : "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (m_bInitSwing)
        {
            try
            {
                UIManager.setLookAndFeel(getLookAndFeel());
            }
            catch(Exception ex)
            {
                if (isDebug())
                    System.err.print("Unable to set look and feel: " + getLookAndFeel() + " " + ex.toString());
            }
        }
        return (m_resBundle != null);
    }
}
