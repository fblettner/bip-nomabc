/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
