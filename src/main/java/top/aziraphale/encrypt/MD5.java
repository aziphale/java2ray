package top.aziraphale.encrypt;

import org.springframework.util.DigestUtils;

public final class MD5 {

    public static byte[] md5(byte[] originByteArr) {
        return DigestUtils.md5Digest(originByteArr);
    }
}
