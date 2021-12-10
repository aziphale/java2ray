package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AES {

    public static byte[] CFBEncrypt(byte[] origin, byte[] secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(origin);
    }

    public static byte[] CFBDecrypt(byte[] message, byte[] secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    public static byte[] GCMEncrypt(byte[] origin, byte[] secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = cipher.getIV();
        assert iv.length == 12;
        byte[] encryptData = cipher.doFinal(origin);
        assert encryptData.length == origin.length + 16;
        byte[] message = new byte[12 + origin.length + 16];
        System.arraycopy(iv, 0, message, 0, 12);
        System.arraycopy(encryptData, 0, message, 12, encryptData.length);
        return message;
    }

    public static byte[] GCMDecrypt(byte[] message, byte[] secretKey) throws Exception {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        if (message.length < 12 + 16) {
            throw new IllegalArgumentException();
        }
        GCMParameterSpec params = new GCMParameterSpec(128, message, 0, 12);
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        return cipher.doFinal(message, 12, message.length - 12);
    }

    private static byte[] generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
}
