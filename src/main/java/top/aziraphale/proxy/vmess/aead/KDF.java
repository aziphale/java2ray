package top.aziraphale.proxy.vmess.aead;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import top.aziraphale.proxy.vmess.VMess;
import top.aziraphale.utils.ByteUtil;

/**
 * @author Sheffery
 * @date 2021/12/19 1:32 PM
 */
public class KDF {

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

@Getter
@Setter
class HMacCreator {

    private HMacCreator parent;
    private byte[] value;

    public HMacCreator(byte[] value) {
        this.value = value;
    }

    public HMacCreator(HMacCreator parent, byte[] value) {
        this.parent = parent;
        this.value = value;
    }

    public RepeatDigest create() {
        if (this.parent == null) {
            HMac hMac = new HMac(new SHA256Digest());
            hMac.init(new KeyParameter(this.value));
            return new RepeatDigest(hMac);
        }
        HMac hMac = new HMac(this.parent.create());
        hMac.init(new KeyParameter(this.value));
        return new RepeatDigest(hMac);
    }
}

class RepeatDigest implements Digest {

    public static final int INIT_BLOCK_SIZE = 32;
    public static final String INIT_ALGORITHM = "SHA-256";

    private final HMac hMac;

    public RepeatDigest(HMac hMac) {
        this.hMac = hMac;
    }

    @Override
    public String getAlgorithmName() {
        return INIT_ALGORITHM;
    }

    @Override
    public int getDigestSize() {
        return INIT_BLOCK_SIZE;
    }

    @Override
    public void update(byte in) {
        hMac.update(in);
    }

    @Override
    public void update(byte[] in, int inOff, int len) {
        hMac.update(in, inOff, len);
    }

    @Override
    public int doFinal(byte[] out, int outOff) {
        return hMac.doFinal(out, outOff);
    }

    @Override
    public void reset() {
        hMac.reset();
    }
}
