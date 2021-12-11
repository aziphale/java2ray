package top.aziraphale.proxy.vmess.enums;

import lombok.Getter;

@Getter
public enum VMessSecurityType {

    AES_CFB(0x00), NONE(0x01), AES_GCM(0x02), CHACHA_POLY(0x03);

    private final int code;

    VMessSecurityType(int code) {
        this.code =  code;
    }
}