/*
 * Main.java
 *
 * Created on 2. Mai 2006, 19:24
 *
 *  Copyright (C) 2. Mai 2006  <Reiner>

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


package jstart;

/**
 *
 * @author reiner
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
//import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import javax.swing.plaf.FontUIResource;

/**
 * This program should run with Java 1.2 so all advanced stuff is removed and
 * sometimes things are hand made.
 * We use it as stub for launching an application and display a warning if the
 * found java version is too old. This is the reason why it must run even
 * with a very old Java version.
 * 
 * Tested with java 1.2.1 under Windows XP SP2
 * 
 */

class JStringHelper
{
    /**
     * simple replace function
     * @param str
     * @param find
     * @param replace
     * @return
     */
    public static String replaceAll(String str, String find, String replace)
    {
        String strBuilder = new String();
        int i, ls, lf;
        ls = str.length();
        lf = find.length();
        for (i=0; i<ls; i++)
        {
            if (ls - i >= lf)
            {
                if (str.substring(i, i+lf).equals(find))
                {
                    strBuilder += replace;
                    i += (lf-1);
                }
                else strBuilder += str.charAt(i);
                
            }
            else
            {
                strBuilder += str.substring(i);
                break;
            }
                
        }
        return strBuilder;
    }
    
    /**
     * simple split function
     * @param str
     * @param sep
     * @return
     */
    public static String[] split(String str, String sep)
    {
        ArrayList strArray = new ArrayList();
        String strBuilder = new String();
        int i, ls, lf;
        ls = str.length();
        lf = sep.length();
        for (i=0; i<ls; i++)
        {
            if (ls - i >= lf)
            {
                if (str.substring(i, i+lf).equals(sep))
                {
                    strArray.add(strBuilder);
                    strBuilder = new String();
                    i += (lf-1);
                }
                else strBuilder += str.charAt(i);
            }
            else
            {
                strBuilder += str.substring(i);
                if (!JStringHelper.isEmpty(strBuilder))
                {
                    strArray.add(strBuilder);
                    strBuilder = new String();
                }
                break;
            }
        }
        if (!JStringHelper.isEmpty(strBuilder))
            strArray.add(strBuilder);
        
        String[] sArray = new String[strArray.size()];
        for (i=0; i<strArray.size(); i++)
            sArray[i] = (String)strArray.get(i);
        return sArray;
    }
    public static boolean isEmpty(String str)
    {
        return (str == null || str.length() == 0);
    }
}

class JURLHelper
{
	public static String getPath(URL url)
	{
		return URLDecoder.decode(url.getFile());
	}
}

public class Main
{
    protected static final String RESFILE = "jstartlang";
    
    /** Creates a new instance of Main */
    public Main()
    {
    }
    
    public static String getClassPath(Object obj, boolean bOnlyPath)
    {
        String path = null;
        if (obj != null)
        {
            String className = obj.getClass().getName();
            int index = className.lastIndexOf(".");
            if (index >= 0) className = className.substring(index + 1, className.length());
            URL url = obj.getClass().getResource(className + ".class");
            if (url != null)
            {
                try
                {
                    String str = url.toString();
                    int endJar = str.indexOf("!/");
                    if (endJar >= 0)
                    {
                        String prefix = "jar:";
                        url = new URL(str.substring(prefix.length(), endJar));
                    }
                    File file = new File(JURLHelper.getPath(url));
                    str = file.getAbsolutePath();
                    if (bOnlyPath)
                    {
                        if ((index = str.lastIndexOf(File.separatorChar)) >= 0)
                        path = str.substring(0, index);
                        else path = str; // ???
                    }
                    else path = str;
                }
                catch(Exception ex)
                {
                }
            }
        }
        return path;
    }

    public static String getJavaExecutablePath()
    {
        String str = System.getProperty("java.home");
        if (str != null)
        {
            str = addSeparator(str);
            str += "bin";
            str = addSeparator(str);
            str += "java";
        }
        else str = "java"; // we hope that there will be a $PATH entry to find it !
        return str;
    }

    public static String addSeparator(String str)
    {
        String nstr = new String(str);
        if (str.length() != 0)
        {
            if (str.charAt(str.length()-1) != File.separatorChar)
            nstr += File.separator;
        }
        return nstr;
    }
    
    public static void addSeparator(StringBuffer stringBuffer)
    {
        if (stringBuffer.length() != 0)
        {
            if (stringBuffer.charAt(stringBuffer.length()-1) != File.separatorChar)
            stringBuffer.append(File.separatorChar);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // System.err.println(JStringHelper.replaceAll("TestDies ist ein Test von replace Test", "Test", "123"));
        /*
         String[] strArray2 = JStringHelper.split("Test Dies ist..ein Test von..split Test", "...");
        int ii;
        for (ii=0; ii<strArray2.length; ii++)
        {
            System.err.println(strArray2[ii]);
        }
        */
        
        Main main = new Main(); 
        StringBuffer strBuffer = new StringBuffer(getClassPath(main, true));
        String fileName = getClassPath(main, false);
        int start = fileName.lastIndexOf(File.separatorChar);
        int end = fileName.lastIndexOf(".");
        addSeparator(strBuffer);
        String path = strBuffer.toString();
        strBuffer.append(fileName.substring(start+1, end));
        strBuffer.append(".ini");

        FileInputStream in = null;
        File file = new File(strBuffer.toString());
        try
        {
            in = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(in);

            String ver = getProperty(prop, "minver");

            if (ver != null)
            {
                String verThis = System.getProperty("java.version");
                if (verThis != null)
                {
                    String[] strArray = JStringHelper.split(verThis, ".");
                    if (strArray.length >= 2)
                    {
                        int verThis1 = new Integer(strArray[0]).intValue();
                        int verThis2 = new Integer(strArray[1]).intValue();
                        strArray = JStringHelper.split(ver, ".");
                        if (strArray.length >= 2)
                        {
                            int ver1 = new Integer(strArray[0]).intValue();
                            int ver2 = new Integer(strArray[1]).intValue();
                            if (verThis1 < ver1 || verThis2 < ver2)
                            {
                                initUI();
                                    // ResourceBundle resBundle = ResourceBundle.getBundle(Main.RESFILE, new Locale("en"));
                                ResourceBundle resBundle = ResourceBundle.getBundle(Main.RESFILE);
                                String msg = resBundle.getString("msg");
                                msg = JStringHelper.replaceAll(msg, "%name%", getProperty(prop, "name"));
                                msg = JStringHelper.replaceAll(msg, "%verthis%", verThis1 + "." + verThis2);
                                msg = JStringHelper.replaceAll(msg, "%ver%", ver1 + "." + ver2);
                                String str = System.getProperty("java.home");
                                str = JStringHelper.replaceAll(str, "\\", "/");
                                msg = JStringHelper.replaceAll(msg, "%javahome%", str);
                                msg = JStringHelper.replaceAll(msg, "%download%", resBundle.getString("download"));
                                JOptionPane.showMessageDialog(null, msg, resBundle.getString("caption"), JOptionPane.ERROR_MESSAGE, new ImageIcon(Main.class.getResource("icon.png")));
                                System.exit(1);
                            }
                        }
                    }
                }
            }

            int i, count = 1;
            while (prop.getProperty("op" + count) != null)
            count++;

            String[] cmdArray = new String[count + 2 + args.length];
            cmdArray[0] = getJavaExecutablePath();
            for (i=1; i<count; i++)
            cmdArray[i] = prop.getProperty("op" + i);
            cmdArray[count] = "-jar";
            String jarFile = getProperty(prop, "jar");
            file = new File(jarFile);
            if (!file.isAbsolute())
            jarFile = path + jarFile;
            cmdArray[count + 1] = jarFile;

            // add arguments for the application
            for (i=0; i<args.length; i++)
            cmdArray[count + 2 + i] = args[i];

            Process process = Runtime.getRuntime().exec(cmdArray);

            // print err and output
            String line;
            BufferedReader bufReader;
            if (getProperty(prop, "err").equals("1"))
            {
                bufReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = bufReader.readLine()) != null)
                    System.err.println(line);
            }
            if (getProperty(prop, "out").equals("1"))
            {
                bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = bufReader.readLine()) != null)
                    System.out.println(line);	    
            }
            if (getProperty(prop, "wait").equals("1"))
            {
                try
                {
                    System.exit(process.waitFor());
                }
                catch (InterruptedException ex)
                {
                    System.exit(-1);
                }
            }
            else System.exit(0);
        }
        catch(FileNotFoundException ex)
        {
            initUI();
            //ResourceBundle resBundle = ResourceBundle.getBundle(Main.RESFILE, new Locale("en"));
            ResourceBundle resBundle = ResourceBundle.getBundle(Main.RESFILE);
            String msg = resBundle.getString("nocfg");
            String str = file.getPath();
            str = JStringHelper.replaceAll(str, "\\", "/");
            msg = JStringHelper.replaceAll(msg, "%cfg%", str);
            JOptionPane.showMessageDialog(null, msg, resBundle.getString("caption_nocfg"), JOptionPane.ERROR_MESSAGE, new ImageIcon(Main.class.getResource("icon.png")));
            System.exit(2);
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());
        }
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
    
    public static String getProperty(Properties prop, String key)
    {
        String str = prop.getProperty(key);
        if (str == null) str = "";
        return str;
    }

    protected static void initUI()
    {
		// Prepare UI to look less uggly
        System.setProperty("swing.aatext", "true");

        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
            {
				FontUIResource fr = (javax.swing.plaf.FontUIResource)value;
				if (!key.toString().equals("InternalFrame.titleFont") && fr.isBold())
					UIManager.put(key, new FontUIResource(fr.deriveFont(java.awt.Font.PLAIN)));
            }
        }
        try
        {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception ex)
        {}
    }
}
