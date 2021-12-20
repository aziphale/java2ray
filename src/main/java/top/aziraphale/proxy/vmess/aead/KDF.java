package top.aziraphale.proxy.vmess.aead;

import top.aziraphale.proxy.vmess.VMess;

/**
 * @author Sheffery
 * @date 2021/12/19 1:11 PM
 */
public class KDF {

    public KDF(byte[] key, String... paths) {
        HMacCreator hMacCreator = new HMacCreator(VMess.KDF_SALT_CONST_VMESS_AEAD_KDF.getBytes());
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                hMacCreator = new HMacCreator(hMacCreator, path.getBytes());
            }
        }
    }
}