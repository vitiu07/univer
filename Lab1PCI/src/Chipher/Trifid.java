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
        String regexp = "(.)(?=.*?\\1)";
        //adjustment of key
        String removedDuplicates = key.replaceAll(regexp, ""); //remove duplicates
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
        keyTable = alphabet.replaceAll(regexp, "");
    }
    public String decrypt(String encryptedMessage)
    {
        int TripletTable[][] = new int[TABLE_DIM][encryptedMessage.length()];
        String localMessage = encryptedMessage.toUpperCase();
        int length = encryptedMessage.length();
        char plainMessage[] = new char[length];
        int row = TABLE_DIM - 1;
        int col = length -1; 
        //create cipher table
        for(int position = 0; position < length; position++)
        {
            char c = localMessage.charAt(position);
            int alphabetPosition = keyTable.indexOf(c);
            TripletTable[row][col] =  alphabetPosition / (TABLE_DIM*TABLE_DIM);//layer
            col--;
           if(col < 0) //start from new row
           {
               row--;
               col = length-1;
           }
            TripletTable[row][col] =  (alphabetPosition % (TABLE_DIM*TABLE_DIM)) / TABLE_DIM;//row
           col--;
           if(col < 0) //start from new row
           {
               row--;
               col = length-1;
           }
            TripletTable[row][col] = alphabetPosition % TABLE_DIM;//column
           col--;
           if(col < 0) //start from new row
           {
               row--;
               col = length-1;
           }
        }
        //find plain text
        for(int position = 0 ; position < length ; position++)
        {
            plainMessage[position] = keyTable.
                    charAt(TripletTable[0][position]*TABLE_DIM*TABLE_DIM+
                           TripletTable[1][position]*TABLE_DIM+
                           TripletTable[2][position]);
        }
        return new String(plainMessage);
    }
    
    public String encrypt(String plainMessage)
    {
        char encryptedMessage[] = new char[plainMessage.length()];
        String localMessage = plainMessage.toUpperCase();
        int TripletTable[][] = new int[TABLE_DIM][plainMessage.length()];//correspondence table
        int length;
         length = plainMessage.length();
        int level = 0;
        int encriptedPosition = 0;
        int elements[] = new int [TABLE_DIM];
        //create cipher table
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
               encryptedMessage[encriptedPosition] = keyTable. //character from key table
                       charAt(TABLE_DIM*TABLE_DIM*elements[0]+TABLE_DIM*elements[1]+elements[2]);
               level = 0;
               encriptedPosition++;
           }
        }
        return new String(encryptedMessage);
    }
}






