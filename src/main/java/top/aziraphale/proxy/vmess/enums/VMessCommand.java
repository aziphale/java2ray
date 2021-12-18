package top.aziraphale.proxy.vmess.enums;


import lombok.Getter;
import top.aziraphale.infra.type.TypeCode;

public enum VMessCommand implements TypeCode<Integer> {

    TCP(0x01), UDP(0x02), MUX(0x03);

    private final int code;

    VMessCommand(int code) {
        this.code =  code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
