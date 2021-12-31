package top.aziraphale.proxy.vmess.aead;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import top.aziraphale.encrypt.AES;
import top.aziraphale.encrypt.CRC;
import top.aziraphale.proxy.vmess.VMess;
import top.aziraphale.utils.ByteUtil;
import top.aziraphale.utils.RandomUtil;

import java.time.Instant;

/**
 * @author Sheffery
 * @date 2021/12/23 9:02 PM
 */
public class AEADHeader {

    /**
     * header AEAD encrypt
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static ByteBuf sealVMessAEADHeader(byte[] key, byte[] data) throws Exception {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        byte[] generatedAuthID = createAuthId(byteBuf, key, Instant.EPOCH.getEpochSecond());

        // header length aead encryption
        byteBuf.clear();
        byteBuf.writeShort(data.length);
        byte[] headerDataLengthBytes = byteBuf.array();
        byte[] nonce = new byte[8];
        RandomUtil.diceByte(nonce);
        byte[] headerLengthKey = KDF.KDF_SPLIT(key, 16, VMess.KDF_SALT_CONST_VMESS_HEADER_PAYLOAD_LENGTH_AEAD_KEY, ByteUtil.toString(generatedAuthID), ByteUtil.toString(nonce));
        byte[] headerLengthNonce = KDF.KDF_SPLIT(key, 12, VMess.KDF_SALT_CONST_VMESS_HEADER_PAYLOAD_LENGTH_AEAD_IV, ByteUtil.toString(generatedAuthID), ByteUtil.toString(nonce));
        byte[] headerLengthEncrypted = AES.GCM_ENCRYPT(headerDataLengthBytes, headerLengthKey, headerLengthNonce, generatedAuthID);

        // header data aead encryption
        byte[] headerKey = KDF.KDF_SPLIT(key, 16, VMess.KDF_SALT_CONST_VMESS_HEADER_PAYLOAD_AEAD_KEY, ByteUtil.toString(generatedAuthID), ByteUtil.toString(nonce));
        byte[] headerNonce = KDF.KDF_SPLIT(key, 12, VMess.KDF_SALT_CONST_VMESS_HEADER_PAYLOAD_AEAD_IV, ByteUtil.toString(generatedAuthID), ByteUtil.toString(nonce));
        byte[] headerEncrypted = AES.GCM_ENCRYPT(data, headerKey, headerNonce, generatedAuthID);

        // assemble encrypt data
        byteBuf.clear();
        byteBuf.writeBytes(generatedAuthID);
        byteBuf.writeBytes(headerLengthEncrypted);
        byteBuf.writeBytes(nonce);
        byteBuf.writeBytes(headerEncrypted);

        return byteBuf;
    }

    public static byte[] createAuthId(ByteBuf byteBuf, byte[] key, long time) throws Exception {
        byteBuf.writeLong(time);
        byteBuf.writeInt(RandomUtil.nextInt());
        byteBuf.writeInt(CRC.CHECK_SUM(byteBuf.array()));
        byte[] encryptKey = KDF.KDF_SPLIT(key, 16, VMess.KDF_SALT_CONST_AUTH_ID_ENCRYPTION_KEY);
        return AES.ECB_ENCRYPT(byteBuf.array(), encryptKey);
    }
}
