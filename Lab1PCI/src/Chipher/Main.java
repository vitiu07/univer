/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chipher;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vicu
 */
public class Main {
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Trifid chipher = new Trifid();
        try {
            chipher.generateKeyTable("aaabacaddat");
            // TODO code application logic here
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}

class Trifid
{
     private String keyTable;
    public void generateKeyTable(String key) throws Exception 
    {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "; //english alphabet
        
        String removedDuplicates = key.replaceAll("(.)(?=.*?\\1)", "");
        removedDuplicates = removedDuplicates.toUpperCase();
        for( char c : removedDuplicates.toCharArray())
        {
            char arr[] = new char[1];
            arr[0] = c;
            String s = new String(arr);
            if(!(alphabet.contains(s)))
            {
                Exception e = new Exception("Ilegal characters in key! Use only letters or space");
                throw e;
            }
        }
        alphabet = alphabet.concat(removedDuplicates);
        keyTable = alphabet.replaceAll("(.)(?=.*?\\1)", "");
        System.out.println(keyTable);
    }
//    public String encrypt(String message)
//    {
//        
//    }
}






