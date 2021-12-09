package top.aziraphale.proxy.vmess;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class OutboundVMessRequest extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byteBuf.writeByte(0x1);
        ctx.writeAndFlush(byteBuf);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
