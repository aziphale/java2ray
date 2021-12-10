package top.aziraphale.proxy.vmess.codec;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.aziraphale.encrypt.SHA;
import top.aziraphale.proxy.vmess.enums.VMessAddressType;
import top.aziraphale.proxy.vmess.enums.VMessCommand;
import top.aziraphale.proxy.vmess.enums.VMessSecurityType;
import top.aziraphale.utils.RandomUtil;

@Getter @Setter @Accessors(chain = true)
public class VMessRequest {

    public static final byte VMESS_VERSION = 0x01;

    public static final byte REQUEST_OPTION_CHUNK_STREAM = 0x01;
    public static final byte REQUEST_OPTION_CONNECTION_REUSE = 0x02;
    public static final byte REQUEST_OPTION_CHUNK_MASKING = 0x04;
    public static final byte REQUEST_OPTION_GLOBAL_PADDING = 0x08;

    public static final byte REQUEST_OPTION_AUTHENTICATED_LENGTH = 0x10;

    private byte version = VMESS_VERSION;
    private byte[] requestBodyIV = new byte[16];
    private byte[] requestBodyKey = new byte[16];
    private byte responseHeader = RandomUtil.nextByte();
    private byte option;
    /**
     * high 4 bits
     */
    private byte paddingLength;
    /**
     * low 4 bits
     */
    private VMessSecurityType securityType;
    private byte reserve;
    private VMessCommand command;
    private Integer port;
    private VMessAddressType addressType;
    private byte[] address;
    /**
     * the byte length decided by paddingLength
     */
    private byte[] random;
    /**
     * 4 bytes
     */
    private byte[] check;

    {
        RandomUtil.diceByte(requestBodyIV);
        setRequestBodyIV(SHA.sha256(requestBodyIV));
        RandomUtil.diceByte(requestBodyKey);
        setRequestBodyKey(SHA.sha256(requestBodyKey));
    }
}