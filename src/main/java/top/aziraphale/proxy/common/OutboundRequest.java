package top.aziraphale.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OutboundRequest extends ChannelInboundHandlerAdapter {

    private final ChannelFuture dstChannelFuture;

    public OutboundRequest(ChannelFuture dstChannelFuture) {
        this.dstChannelFuture = dstChannelFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("forwarding client request to target server");
        if (dstChannelFuture.channel().isActive()) {
            if (log.isDebugEnabled()) {
                log.debug("client request is\n{}", msg == null ? "" : ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
            }
            dstChannelFuture.channel().writeAndFlush(msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (dstChannelFuture.channel().isActive()) {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("OutboundHttpRequest Exception", cause);
        ctx.close();
    }
}