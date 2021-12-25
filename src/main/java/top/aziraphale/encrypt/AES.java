package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AES {

    public static byte[] ECBEncrypt(byte[] origin, byte[] secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(origin);
    }

    public static byte[] ECBDecrypt(byte[] message, byte[] secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    public static byte[] GCMEncrypt(byte[] origin, byte[] secretKey, byte[] nonce, byte[] additionData)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NOPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, nonce);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
        cipher.updateAAD(additionData);
        return cipher.doFinal(origin);
    }

    public static byte[] GCMDecrypt(byte[] message, byte[] secretKey, byte[] nonce, byte[] additionData)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        SecretKey key = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/GCM/NOPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, nonce);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
        cipher.updateAAD(additionData);
        return cipher.doFinal(message);
    }
}
