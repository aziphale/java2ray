package top.aziraphale.proxy.vmess;

import top.aziraphale.proxy.common.handler.RayOutHandler;

public abstract class VMess implements RayOutHandler {

    public static final String SALT = "c48619fe-8f02-49e0-b9e9-edf763e17e21";
    public static final String KDF_SALT_CONST_VMESS_AEAD_KDF = "VMess AEAD KDF";
    public static final String KDF_SALT_CONST_AUTH_ID_ENCRYPTION_KEY = "AES Auth ID Encryption";
}
