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
        //adjustment of key
        String removedDuplicates = key.replaceAll("(.)(?=.*?\\1)", ""); //remove duplicates
        removedDuplicates = removedDuplicates.toUpperCase();//convert to uppercase
        for( char c : removedDuplicates.toCharArray())//check if all symbols are in alphabet
        {
            char arr[] = new char[1];
            arr[0] = c;
            String s = new String(arr);
            if(!(alphabet.contains(s))) // if 
            {
                Exception e = new Exception("Ilegal characters in key! Use only letters or space");
                throw e;
            }
        }
        alphabet = alphabet.concat(removedDuplicates);
        //set key table consisting of adjusted (alphabet - key) U key
        keyTable = alphabet.replaceAll("(.)(?=.*?\\1)", "");
    }
    public String encrypt(String message)
    {
        String encriptedMessage;
        
    }
}






