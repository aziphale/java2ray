package top.aziraphale.proxy.vmess.aead;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.Digest;

/**
 * @author Sheffery
 * @date 2021/12/19 1:32 PM
 */
@Getter
@Setter
class HMacCreator {

    private static final String ROOT_MAC_NAME = "SHA-256";

    private HMacCreator parent;
    private byte[] value;

    public HMacCreator(byte[] value) {
        this.value = value;
    }

    public HMacCreator(HMacCreator parent, byte[] value) {
        this.parent = parent;
        this.value = value;
    }

    public Digest create() {
        if (this.parent == null) {
            return new Digest() {
                @Override
                public String getAlgorithmName() {
                    return null;
                }

                @Override
                public int getDigestSize() {
                    return 0;
                }

                @Override
                public void update(byte in) {

                }

                @Override
                public void update(byte[] in, int inOff, int len) {

                }

                @Override
                public int doFinal(byte[] out, int outOff) {
                    return 0;
                }

                @Override
                public void reset() {

                }
            };
            /*return (data -> {
                SHA256Digest digest = new SHA256Digest();
                HMac mac = new HMac(digest);
                mac.init(new KeyParameter(this.value));
                byte[] encrypt = new byte[digest.getDigestSize()];
                mac.doFinal(encrypt, 0);
                return encrypt;
            });*/
        }
        return null;
    }
}
