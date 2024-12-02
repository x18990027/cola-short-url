package com.cola.shortUrl.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryptionUtils {
    private static final String ALGORITHM = "AES"; // 指定加密算法为AES
    private static final byte[] KEY_BYTES = "xcx2000521abcdef".getBytes(); // 设置固定的Key值

//    public static void main(String[] args) throws Exception {
//        String originalText = "Hello World!"; // 要加密或解密的原始文本
//
//        System.out.println("Original Text: " + originalText);
//
//        // 加密过程
//        Cipher cipherEncrypt = getCipherInstance(true);
//        byte[] encryptedData = encrypt(cipherEncrypt, originalText.getBytes());
//        String encodedEncryptedData = Base64.getEncoder().encodeToString(encryptedData);
//        System.out.println("Encrypted Data (base64): " + encodedEncryptedData);
//
//        // 解密过程
//        Cipher cipherDecrypt = getCipherInstance(false);
//        byte[] decodedEncryptedData = Base64.getDecoder().decode(encodedEncryptedData);
//        byte[] decryptedData = decrypt(cipherDecrypt, decodedEncryptedData);
//        String decryptedText = new String(decryptedData);
//        System.out.println("Decrypted Text: " + decryptedText);
//    }

    //加密
    public   static String encode(String originalText) {
        // 加密过程
        try {
            Cipher cipherEncrypt = getCipherInstance(true);
            byte[] encryptedData = encrypt(cipherEncrypt, originalText.getBytes());
            String encodedEncryptedData = Base64.getEncoder().encodeToString(encryptedData);
            return encodedEncryptedData;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //解密
    public static   String getSrt(String encodedEncryptedData) {
        // 解密过程
        try {
            Cipher cipherDecrypt = getCipherInstance(false);
            byte[] decodedEncryptedData = Base64.getDecoder().decode(encodedEncryptedData);
            byte[] decryptedData = decrypt(cipherDecrypt, decodedEncryptedData);
            String decryptedText = new String(decryptedData);
            return decryptedText;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    private static Cipher getCipherInstance(boolean isEncryptMode) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY_BYTES, ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            if (isEncryptMode) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            }
            return cipher;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static byte[] encrypt(Cipher cipher, byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(input);
    }

    private static byte[] decrypt(Cipher cipher, byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(input);
    }
}
