package com.example.aesencryption;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;

    public static byte[] encrypt(String data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }

    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static Key generateKey() throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[KEY_SIZE];
        random.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
