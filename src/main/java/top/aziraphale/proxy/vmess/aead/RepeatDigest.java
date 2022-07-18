package top.aziraphale.proxy.vmess.aead;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;

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
