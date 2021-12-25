package top.aziraphale.proxy.vmess.out;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import top.aziraphale.encrypt.FNV;
import top.aziraphale.exception.FieldSoLongException;
import top.aziraphale.proxy.common.OutboundRequest;
import top.aziraphale.proxy.vmess.aead.AEADHeader;
import top.aziraphale.proxy.vmess.codec.VMessRequest;
import top.aziraphale.proxy.vmess.enums.VMessAddressType;
import top.aziraphale.utils.ByteUtil;
import top.aziraphale.utils.RandomUtil;

public class OutboundVMessRequest extends OutboundRequest {

    public OutboundVMessRequest(ChannelFuture dstChannelFuture) {
        super(dstChannelFuture);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf src = (ByteBuf) msg;
        // TODO serial vmess packet
        VMessRequest request = new VMessRequest();
        ByteBuf dst = encodeRequestHeader(request);
        dstChannelFuture.channel().writeAndFlush(dst);
    }

    private ByteBuf encodeRequestHeader(VMessRequest request) throws Exception {
        ByteBuf writer = ByteBufAllocator.DEFAULT.heapBuffer();
        writer.writeByte(VMessRequest.VMESS_VERSION);
        writer.writeBytes(request.getRequestBodyIV());
        writer.writeBytes(request.getRequestBodyKey());
        writer.writeByte(request.getResponseHeader());
        writer.writeByte(request.getOption());
        writer.writeByte(request.getPaddingLength() << 4 | request.getSecurityType().getCode());
        writer.writeByte(request.getReserve());
        writer.writeByte(request.getCommand().getCode());
        writer.writeShort(request.getPort());
        writer.writeByte(request.getAddressType().getCode());
        if (VMessAddressType.IPV4.equals(request.getAddressType()) || VMessAddressType.IPV6.equals(request.getAddressType())) {
            writer.writeBytes(request.getAddress());
        } else if (VMessAddressType.DOMAIN.equals(request.getAddressType())) {
            if (request.getAddress().length > 256) {
                throw new FieldSoLongException("domain name", 256);
            }
            writer.writeByte(request.getAddress().length);
            writer.writeBytes(request.getAddress());
        }
        if (request.getPaddingLength() > 0) {
            byte[] padding = new byte[request.getPaddingLength()];
            RandomUtil.diceByte(padding);
            writer.writeBytes(padding);
        }
        writer.writeBytes(ByteUtil.tail(FNV.FNV1A_32(writer.array()).toByteArray(), 4));

        // only for develop
        byte[] salt = new byte[]{(byte) 39, (byte) -124, (byte) -121, (byte) 57, (byte) 126, (byte) 98, (byte) 65, (byte) 56, (byte) -97, (byte) -45, (byte) 9, (byte) -118, (byte) 99, (byte) -106, (byte) 75, (byte) 107, (byte) 99, (byte) 52, (byte) 56, (byte) 54, (byte) 49, (byte) 57, (byte) 102, (byte) 101, (byte) 45, (byte) 56, (byte) 102, (byte) 48, (byte) 50, (byte) 45, (byte) 52, (byte) 57, (byte) 101, (byte) 48, (byte) 45, (byte) 98, (byte) 57, (byte) 101, (byte) 57, (byte) 45, (byte) 101, (byte) 100, (byte) 102, (byte) 55, (byte) 54, (byte) 51, (byte) 101, (byte) 49, (byte) 55, (byte) 101, (byte) 50, (byte) 49};

        return AEADHeader.sealVMessAEADHeader(salt, writer.array());
    }
}
