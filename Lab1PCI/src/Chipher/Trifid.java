/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chipher;

/**
 *
 * @author vicu
 */

class Trifid
{
     private String keyTable;
     private final int TABLE_DIM = 3;
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
            if(!(alphabet.contains(s))) //if symbol is not in alphabet
            {
                Exception e = new Exception("Ilegal characters in key! Use only letters or space");
                throw e;
            }
        }
        alphabet = alphabet.concat(removedDuplicates);
        //set key table consisting of adjusted (alphabet - key) U key
        keyTable = alphabet.replaceAll("(.)(?=.*?\\1)", "");
    }
    public String decrypt(String encryptedMessage)
    {
        return new String();
    }
    
    public String encrypt(String plainMessage)
    {
        char encriptedMessage[] = new char[plainMessage.length()];
        String localMessage = plainMessage.toUpperCase();
        int TripletTable[][] = new int[TABLE_DIM][plainMessage.length()];
        int length;
         length = plainMessage.length();
        int level = 0;
        int encriptedPosition = 0;
        int elements[] = new int [TABLE_DIM];
        for(int position = 0; position < length; position++)
        {
            char c = localMessage.charAt(position);
            int alphabetPosition = keyTable.indexOf(c);
            int layer = alphabetPosition / (TABLE_DIM*TABLE_DIM);//table layer
            TripletTable[0][position] = layer;
            TripletTable[1][position] = (alphabetPosition % (TABLE_DIM*TABLE_DIM)) / TABLE_DIM;//row
            TripletTable[2][position] = alphabetPosition % TABLE_DIM;//column
        }
        //find encripted character position
        for(int i = TABLE_DIM*length - 1;i >= 0;i--)
        {
           int col = i % length;
           int row = i / length;
           //element[0] - layer, element[1] - row, element[2] - column
           elements[level] = TripletTable[row][col];
           level++;
           if(level == TABLE_DIM)
           {
               encriptedMessage[encriptedPosition] = keyTable. //character from key table
                       charAt(TABLE_DIM*TABLE_DIM*elements[0]+TABLE_DIM*elements[1]+elements[2]);
               level = 0;
               encriptedPosition++;
           }
        }
        return new String(encriptedMessage);
    }
}






