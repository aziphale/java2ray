package top.aziraphale.proxy.vmess.aead;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

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
