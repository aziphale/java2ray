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
     * @param src
     * @return
     */
    public static byte[] SHAKE_128(byte[] src) {
        Digest digest = new SHAKEDigest(128);
        byte[] array = new byte[digest.getDigestSize()];
        digest.update(src, 0, src.length);
        digest.doFinal(array, 0);
        return array;
    }

    /**
     * @param src origin data
     * @return the SHA256 checksum of the data
     */
    public static byte[] SHA_256(byte[] src) {
        SHA256Digest digest = new SHA256Digest();
        byte[] array = new byte[digest.getDigestSize()];
        digest.update(src, 0, src.length);
        digest.doFinal(array, 0);
        return array;
    }
}
