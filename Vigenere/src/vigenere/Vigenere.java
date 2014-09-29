/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

/**
 *
 * @author vicu
 */
public class Vigenere {
    //chipher alphbet
    protected static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    protected static int length = alphabet.length();
    public String encrypt(String plainMessage, String key)
    {
        char encripted[] = new char[plainMessage.length()];
        int plain_length = plainMessage.length();
        int key_length = key.length();
        for(int i=0;i<plain_length;i++)
        {  //Ci = ( Mi + K(i mod n) ) mod alphabet_len
            encripted[i] = alphabet.charAt((alphabet.indexOf(plainMessage.charAt(i)) +
                           alphabet.indexOf(key.charAt(i % key_length)))% length);
        }
        return new String(encripted);
    }
    public String decrypt(String encryptedMessage, String key)
    {
        char decripted[] = new char[encryptedMessage.length()];
        int plain_length = encryptedMessage.length();
        int key_length = key.length();
        for(int i=0;i<plain_length;i++)
        {
            int result = alphabet.indexOf(encryptedMessage.charAt(i)) -
                         alphabet.indexOf(key.charAt(i % key_length));
            if (result < 0 ) 
                    {
                        result+= length ;
            }
            //Mi = ( Ci - K(i mod n) ) mod alphabet_len
            result %= length;
            decripted[i] = alphabet.charAt(result);
        }
        return new String(decripted);
    }
    
}
