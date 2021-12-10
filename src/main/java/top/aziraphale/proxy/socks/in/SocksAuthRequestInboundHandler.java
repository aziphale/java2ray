package top.aziraphale.proxy.socks.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.socksx.v5.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocksAuthRequestInboundHandler extends SimpleChannelInboundHandler<DefaultSocks5PasswordAuthRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultSocks5PasswordAuthRequest msg) throws Exception {
        Socks5PasswordAuthResponse passwordAuthResponse = new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.SUCCESS);
        ctx.writeAndFlush(passwordAuthResponse);
        ctx.pipeline().remove(this);
        ctx.pipeline().remove(Socks5PasswordAuthRequestDecoder.class);
    }
}