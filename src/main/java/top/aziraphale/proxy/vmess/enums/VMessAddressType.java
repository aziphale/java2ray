package top.aziraphale.proxy.vmess.enums;

import lombok.Getter;

@Getter
public enum VMessAddressType {

    IPV4(0x01), DOMAIN(0x02), IPV6(0x03);

    private final int code;

    VMessAddressType(int code) {
        this.code =  code;
    }
}