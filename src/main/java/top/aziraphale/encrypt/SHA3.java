package top.aziraphale.encrypt;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;

public class SHA3 {

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
}
