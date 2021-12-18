package top.aziraphale.proxy.vmess.enums;

import lombok.Getter;
import top.aziraphale.infra.type.TypeCode;

public enum VMessAddressType implements TypeCode<Integer> {

    IPV4(0x01), DOMAIN(0x02), IPV6(0x03);

    private final int code;

    VMessAddressType(int code) {
        this.code =  code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}