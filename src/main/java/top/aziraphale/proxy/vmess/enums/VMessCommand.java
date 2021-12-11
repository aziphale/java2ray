package top.aziraphale.proxy.vmess.enums;


import lombok.Getter;

@Getter
public enum VMessCommand {

    TCP(0x01), UDP(0x02);

    private final int code;

    VMessCommand(int code) {
        this.code =  code;
    }
}
