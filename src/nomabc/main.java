/*
 * Copyright (c) 2018 NOMANA-IT and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * @author fblettner
 */
package nomabc;

import static nomabc.BarcodeUtil.code128;

/**
 *
 * @author franckblettner
 */
public class main {

   public static void main(String[] args) throws Exception
    {
    System.out.println(code128(args[0]));
    }
    
}
