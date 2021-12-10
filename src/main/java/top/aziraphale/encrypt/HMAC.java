package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HMAC {

    private static final String MAC_NAME = "HmacMD5";

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText
     * @param encryptKey
     * @return encrypted byte array
     * @throws Exception
     */
    public static byte[] HmacMD5Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(StandardCharsets.UTF_8);
        return mac.doFinal(text);
    }
}