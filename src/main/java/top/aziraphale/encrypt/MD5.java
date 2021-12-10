package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MD5 {

    public static byte[] md5(byte[] originByteArr) {
        return DigestUtils.md5Digest(originByteArr);
    }
}
