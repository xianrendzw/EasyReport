package com.easytoolsoft.easyreport.data.common.helper;

//import java.io.InputStream;
//import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
//import javax.crypto.CipherInputStream;
//import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <一句话功能简述>
 */
public class AESHelper {
    private static Cipher ecipher;
    
    private static Cipher dcipher;
    
    static {
    	setKey("kfsjdfoas");
    }
    /**
     * Input a string that will be md5 hashed to create the key.
     * 
     * @return void, cipher initialized
     */
//    public AESHelper() {
//        try {
//            PropertiesUtils.configure( getClass().getResource("config/config.properties").getPath());
//            if(StringUtils.isBlank(PropertiesUtils.getValue("easyreport.common.aeskey"))){
//                SecretKeySpec skey = new SecretKeySpec(getMD5(PropertiesUtils.getValue("easyreport.common.aeskey")), "AES");
//                this.setupCrypto(skey);
//            }else{
//                this.setupCrypto(null);
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public AESHelper(String key) {
//        SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
//        this.setupCrypto(skey);
//    }
    
    public static void setKey(String key){
        SecretKeySpec skey = new SecretKeySpec(getMD5(key), "AES");
        setupCrypto(skey);
    }
    
    private static void setupCrypto(SecretKey key) {
        if(key==null){
            return;
        }
        // Create an 8-byte initialization vector
        byte[] iv = new byte[]
        {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        try {
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            // CBC requires an initialization vector
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Buffer used to transport the bytes from one stream to another
    // byte[] buf = new byte[1024];
    
//    public void encrypt(InputStream in, OutputStream out) {
//        byte[] buf = new byte[1024];
//        try {
//            // Bytes written to out will be encrypted
//            out = new CipherOutputStream(out, ecipher);
//            
//            // Read in the cleartext bytes and write to out to encrypt
//            int numRead = 0;
//            while ((numRead = in.read(buf)) >= 0) {
//                out.write(buf, 0, numRead);
//            }
//            out.close();
//        }
//        catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * Input is a string to encrypt.
     * 
     * @return a Hex string of the byte array
     */
    public static String encrypt(String plaintext) {
        if(ecipher==null){
            return plaintext;
        }
        try {
            byte[] ciphertext = ecipher.doFinal(plaintext.getBytes("UTF-8"));
            return byteToHex(ciphertext);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
//    public void decrypt(InputStream in, OutputStream out) {
//        try {
//            byte[] buf = new byte[1024];
//            // Bytes read from in will be decrypted
//            in = new CipherInputStream(in, dcipher);
//            
//            // Read in the decrypted bytes and write the cleartext to out
//            int numRead = 0;
//            while ((numRead = in.read(buf)) >= 0) {
//                out.write(buf, 0, numRead);
//            }
//            out.close();
//        }
//        catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * Input encrypted String represented in HEX
     * 
     * @return a string decrypted in plain text
     */
    public static String decrypt(String hexCipherText) {
        if(dcipher==null){
            return hexCipherText;
        }
        try {
            String plaintext = new String(dcipher.doFinal(hexToByte(hexCipherText)), "UTF-8");
            return plaintext;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String decrypt(byte[] ciphertext) {
        try {
            String plaintext = new String(dcipher.doFinal(ciphertext), "UTF-8");
            return plaintext;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static byte[] getMD5(String input) {
        try {
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(bytesOfMessage);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static final String HEXES = "0123456789ABCDEF";
    
    public static String byteToHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
    
    public static byte[] hexToByte(String hexString) {
        int len = hexString.length();
        byte[] ba = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            ba[i / 2] =
                (byte)((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return ba;
    }
    
}