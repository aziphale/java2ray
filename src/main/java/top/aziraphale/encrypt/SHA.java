package top.aziraphale.encrypt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHAKEDigest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SHA {

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
        SHA256Digest digest = new SHA256Digest();
        byte[] array = new byte[digest.getDigestSize()];
        digest.doFinal(array, 0);
        return array;
    }
}
