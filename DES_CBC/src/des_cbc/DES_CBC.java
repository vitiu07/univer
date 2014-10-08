/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des_cbc;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 http://www.vocal.com/cryptography/data-encryption-standard-des/
 http://en.wikipedia.org/wiki/Block_cipher_mode_of_operation
 * @author Budwar
 */
public class DES_CBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
                byte[] keyBytes = new byte[] { 0x54, 0x23, 0x25, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd,
    (byte) 0xef};
                byte[] ivBytes = new byte[]{0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};

                // Create secret key
                SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
                Cipher desCipher;
                // Create the cipher 
                desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                //Initial vector
                IvParameterSpec ivectorSpecv = new IvParameterSpec(ivBytes);
                // Initialize the cipher for encryption
                desCipher.init(Cipher.ENCRYPT_MODE, key, ivectorSpecv);
                //sensitive information
                Scanner in = new Scanner(System.in);
                System.err.println("Get plain text:");
                String input_text = in.next();
                byte[] text = input_text.getBytes();
                // Encrypt the text=
                byte[] textEncrypted = desCipher.doFinal(text);
                System.out.println("Encryted text: " + textEncrypted);
                // Initialize the same cipher for decryption
                desCipher.init(Cipher.DECRYPT_MODE, key,ivectorSpecv);
                // Decrypt the text
                byte[] textDecrypted = desCipher.doFinal(textEncrypted);
                System.out.println("Decryted text: " + new String(textDecrypted));

            }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
                    e.printStackTrace();
            } catch (InvalidAlgorithmParameterException ex) { 
            Logger.getLogger(DES_CBC.class.getName()).log(Level.SEVERE, null, ex);
        } 
 
    }
    
}
