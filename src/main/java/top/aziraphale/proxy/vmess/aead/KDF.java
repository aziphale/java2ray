package top.aziraphale.proxy.vmess.aead;

import org.bouncycastle.crypto.Digest;
import top.aziraphale.proxy.vmess.VMess;
import top.aziraphale.utils.ByteUtil;

/**
 * @author Sheffery
 * @date 2021/12/19 1:32 PM
 */
class KDF {

    public static byte[] KDF_FULL(byte[] key, String... paths) {
        HMacCreator hMacCreator = new HMacCreator(VMess.KDF_SALT_CONST_VMESS_AEAD_KDF.getBytes());
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                hMacCreator = new HMacCreator(hMacCreator, path.getBytes());
            }
        }
        Digest digest = hMacCreator.create();
        digest.update(key, 0, key.length);
        byte[] result = new byte[RepeatDigest.INIT_BLOCK_SIZE];
        digest.doFinal(result, 0);
        return result;
    }

    public static byte[] KDF_SPLIT(byte[] key, int length, String... paths) {
        if (length < 0) {
            throw new IllegalArgumentException("length : " + length + " (expected: > 0)");
        }
        if (length > 32) {
            throw new IllegalArgumentException("length : " + length + " (expected: <= 32)");
        }
        return ByteUtil.head(KDF_FULL(key, paths), length);
    }
}

