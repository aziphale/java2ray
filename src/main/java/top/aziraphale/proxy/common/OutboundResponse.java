package top.aziraphale.proxy.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OutboundResponse extends ChannelInboundHandlerAdapter {

    private final ChannelHandlerContext clientChannelHandlerContext;

    public OutboundResponse(ChannelHandlerContext clientChannelHandlerContext) {
        this.clientChannelHandlerContext = clientChannelHandlerContext;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("write back");
        if (clientChannelHandlerContext.channel().isActive()) {
            if (log.isDebugEnabled()) {
                log.debug("write back message is\n{}", msg == null ? "" : ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
            }
            clientChannelHandlerContext.writeAndFlush(msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("proxy server has disconnected with target server");
        if (clientChannelHandlerContext.channel().isActive()) {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("OutboundHttpResponse Exception", cause);
        ctx.close();
    }
}

