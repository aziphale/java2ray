package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SHA {

    /**
     * SHA3-Shake128
     * @param bytes
     * @return
     */
    public static byte[] shake128(byte[] bytes) {
        Digest digest = new SHAKEDigest(128);
        digest.update(bytes, 0, bytes.length);
        byte[] date = new byte[digest.getDigestSize()];
        digest.doFinal(date, 0);
        return date;
    }

    /**
     * @param origin data
     * @return the SHA256 checksum of the data
     */
    public static byte[] sha256(byte[] origin) {
        return DigestUtils.sha256(origin);
    }
}
