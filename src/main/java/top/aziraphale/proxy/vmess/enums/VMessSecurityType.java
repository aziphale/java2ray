package top.aziraphale.proxy.vmess.enums;

import lombok.Getter;
import top.aziraphale.infra.type.TypeCode;

public enum VMessSecurityType implements TypeCode<Integer> {

    AES_CFB(0x00), NONE(0x01), AES_GCM(0x02), CHACHA_POLY(0x03);

    private final int code;

    VMessSecurityType(int code) {
        this.code =  code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}