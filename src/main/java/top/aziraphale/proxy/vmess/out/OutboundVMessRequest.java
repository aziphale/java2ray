package top.aziraphale.proxy.vmess.out;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import top.aziraphale.proxy.common.OutboundRequest;

public class OutboundVMessRequest extends OutboundRequest {

    public OutboundVMessRequest(ChannelFuture dstChannelFuture) {
        super(dstChannelFuture);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        // TODO serial vmess packet
        super.channelRead(ctx, byteBuf);
    }
}
