/*
 * Copyright (c) 2018 NOMANA-IT and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * @author fblettner
 */
package nomabc;

import java.util.Hashtable;
import java.lang.reflect.Method;
import oracle.xdo.template.rtf.util.XDOBarcodeEncoder;
import oracle.xdo.common.log.Logger;
// This class name will be used in the register vendor field in the template.


public class BarcodeUtil implements XDOBarcodeEncoder
// The class implements the XDOBarcodeEncoder interface
{
// This is the barcode vendor id that is used in the register vendor field and format-barcode fields
  public static final String BARCODE_VENDOR_ID = "NOMANA";
// The hastable is used to store references to the encoding methods
  public static final Hashtable ENCODERS = new Hashtable(10);
// The BarcodeUtil class needs to be instantiated
  public static final BarcodeUtil mUtility = new BarcodeUtil();
// This is the main code that is executed in the class, it is loading the methods for the encoding into the hashtable. In this case we are loading the three code128 encoding methods we have created.
  static {
    try {
      Class[] clazz = new Class[] { "".getClass() };
      
      ENCODERS.put("code128",mUtility.getClass().getMethod("code128", clazz));
     } catch (Exception e) {
 // This is using the XML Publisher logging class to push errors to the XMLP log file.
      Logger.log(e,5);
    }
  }



// The getVendorID method is called from the template layer at runtime to ensure the correct encoding method are used
    public final String getVendorID()
    {
        return BARCODE_VENDOR_ID;
    }
//The isSupported method is called to ensure that the encoding method called from the template is actually present in this class. If not then XMLP will report this in the log.
    public final boolean isSupported(String s)
    {
        if(s != null)
            return ENCODERS.containsKey(s.trim().toLowerCase());
        else
            return false;
    }



// The encode method is called to then call the appropriate encoding method, in this example the code128a/b/c methods.



   public final String encode(String s, String s1)
    {
        if(s != null && s1 != null)
        {
            try
            {
                Method method = (Method)ENCODERS.get(s1.trim().toLowerCase());
                if(method != null)
                    return (String)method.invoke(this, new Object[] {
                        s
                    });
                else
                    return s;
            }
            catch(Exception exception)
            {
                  Logger.log(exception,5);
            }
            return s;
        } else
        {
            return s;
        }
    }



  private static int testNumeric(char[] text, int i, int mini)
  {
    mini--;
    if (i + mini < text.length)
      for (; mini >= 0; mini--)
        if ((text[i + mini] < 48) || (text[i + mini] > 57))
          break;
    return mini;
  }


  public static final String code128( String DataToEncode )
  {
    char[] text = DataToEncode.toCharArray();
    int checksum = 0; // caractËre de vÈrification du texte codÈ
    int mini; // nb de caractËres numÈriques suivants
    int char2; // traitement de 2 caractËres ‡ la fois
    boolean tableB = true; // boolÈen pour vÈrifier si on doit utiliser la table B du code 128

    String code128 = "";

    for (char c : text)
      if ((c < 32) || (c > 126))
        return null;

    for (int i = 0; i < text.length;)
    {
      if (tableB)
      {
        // intÈressant de passer en table C pour 4 chiffres au dÈbut ou a la fin ou pour 6 chiffres
        mini = ((i == 0) || (i + 3 == text.length - 1) ? 4 : 6);

        // si les mini caractËres ‡ partir de index sont numÈriques, alors mini = 0
        mini = testNumeric(text, i, mini);

        // si mini < 0 on passe en table C
        if (mini < 0)
        {
          code128 += (char) (i == 0 ? 210 : 204); // dÈbuter sur la table C ou commuter sur la table C
          tableB = false;
        }
        else if (i == 0)
          code128 += (char) 209; // dÈbuter sur la table B
      }

      if (!tableB)
      {
        // on est sur la table C, on va essayer de traiter 2 chiffres
        mini = testNumeric(text, i, 2);

        if (mini < 0)
        {
          // ok pour 2 chiffres, les traiter
          char2 = Integer.parseInt("" + text[i] + text[i + 1]);
          char2 += (char2 < 95 ? 32 : 105);
          code128 += (char) char2;
          i += 2;
        }
        else
        {
          // on n'a pas deux chiffres, retourner en table B
          code128 += (char) 205;
          tableB = true;
        }
      }

      if (tableB)
        code128 += text[i++];
    }

    // calcul de la clef de controle
    for (int i = 0; i < code128.length(); i++)
    {
      char2 = code128.charAt(i);
      char2 -= (char2 < 127 ? 32 : 105);
      checksum = ((i == 0 ? char2 : checksum) + i * char2) % 103;
    }

    // calcul du code ascii de la clef de controle
    checksum += (checksum < 95 ? 32 : 105);

    // ajout de la clef et du stop ‡ la fin du texte codÈ.
    return code128 += ("" + (char) checksum + (char) 211);
 }

    
}