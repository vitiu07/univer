/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des_ecb;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 http://www.vocal.com/cryptography/data-encryption-standard-des/
 http://en.wikipedia.org/wiki/Block_cipher_mode_of_operation
 * @author Budwar
 */
public class DES_ECB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
                byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,
    (byte) 0xef};
                SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");

                Cipher desCipher;

                // Create the cipher 
                desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

                // Initialize the cipher for encryption
                desCipher.init(Cipher.ENCRYPT_MODE, key);

                //sensitive information
                byte[] text = "No body can see me".getBytes();

                System.out.println("Text [Byte Format] : " + text);
                System.out.println("Text : " + new String(text));

                // Encrypt the text
                byte[] textEncrypted = desCipher.doFinal(text);

                System.out.println("Text Encryted : " + textEncrypted);

                // Initialize the same cipher for decryption
                desCipher.init(Cipher.DECRYPT_MODE, key);

                // Decrypt the text
                byte[] textDecrypted = desCipher.doFinal(textEncrypted);

                System.out.println("Text Decryted : " + new String(textDecrypted));

            }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
                    e.printStackTrace();
            } 
 
    }
    
}
