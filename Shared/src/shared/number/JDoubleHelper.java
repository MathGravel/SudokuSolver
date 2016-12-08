/** Created on 06.11.2008 11:40:06
 *
 * Copyright (C) 2008  PSYSTEME GmbH  Georg-Hallmaier-Str. 6  D-81369 Muenchen.
 *
 * @author reiner
 */

package shared.number;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Parse a double value from a string using the us locale (.)
 * and a defined locale
 */
public class JDoubleHelper
{
    public JDoubleHelper()
    {
    }
    
    public static double parseDouble(String str, Locale locale) throws NumberFormatException
    {
        double val;
        try
        {
            val = Double.parseDouble(str);
        }
        catch(NumberFormatException ex)
        {
            try
            {
                NumberFormat numberFormat = NumberFormat.getInstance(locale);
                val = numberFormat.parse(str).doubleValue();
            }
            catch(ParseException e)
            {
                throw new NumberFormatException(e.getMessage());
            }
        }
        return val;
    }
    
    public static double parseDouble(String str) throws NumberFormatException
    {
        return parseDouble(str, Locale.getDefault());
    }
}
