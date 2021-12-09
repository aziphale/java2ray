package top.aziraphale.proxy.vmess.codec;

import lombok.Getter;
import lombok.Setter;
import top.aziraphale.proxy.vmess.auth.VmessUser;

@Getter @Setter
public class VMessRequest {

    public static final byte VMESS_VERSION = 0x01;

    public static final byte REQUEST_OPTION_CHUNK_STREAM = 0x01;
    public static final byte REQUEST_OPTION_CONNECTION_REUSE = 0x02;
    public static final byte REQUEST_OPTION_CHUNK_MASKING = 0x04;
    public static final byte REQUEST_OPTION_GLOBAL_PADDING = 0x08;

    public static final byte REQUEST_OPTION_AUTHENTICATED_LENGTH = 0x10;

    private byte version = VMESS_VERSION;
    private Command command;
    private byte option;
    private SecurityType securityType;
    private Integer port;
    private String address;
    private AddressType addressType;
    private VmessUser user;

    @Getter
    public enum Command {
        TCP(0x01), UDP(0x02);

        private final int code;

        Command(int code) {
            this.code =  code;
        }
    }

    @Getter
    public enum SecurityType {
        AES_CFB(0x00), NONE(0x01), AES_GCM(0x02), CHACHA_POLY(0x03);

        private final int code;

        SecurityType(int code) {
            this.code =  code;
        }
    }

    @Getter
    public enum AddressType {
        IPV4(0x01), DOMAIN(0x02), IPV6(0x03);

        private final int code;

        AddressType(int code) {
            this.code =  code;
        }
    }
}
