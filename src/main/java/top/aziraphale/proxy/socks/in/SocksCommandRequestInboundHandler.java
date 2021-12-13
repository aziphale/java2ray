package top.aziraphale.proxy.socks.in;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socksx.v5.*;
import lombok.extern.slf4j.Slf4j;
import top.aziraphale.proxy.common.OutboundRequest;
import top.aziraphale.proxy.common.OutboundResponse;
import top.aziraphale.proxy.vmess.out.OutboundVMessRequest;
import top.aziraphale.proxy.vmess.out.OutboundVMessResponse;

@Slf4j
public class SocksCommandRequestInboundHandler extends SimpleChannelInboundHandler<DefaultSocks5CommandRequest> {

    private final Bootstrap clientBootstrap;

    public SocksCommandRequestInboundHandler(Bootstrap clientBootstrap) {
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg) throws Exception {
        Socks5AddressType socks5AddressType = msg.dstAddrType();
        log.debug("socks command type is {}", msg.type());
        // TODO just handle connect command in this version
        if (Socks5CommandType.CONNECT.equals(msg.type())) {
            log.debug("target address is {} port is {}", msg.dstAddr(), msg.dstPort());
            directConnect(ctx, msg, socks5AddressType);
        } else {
            ctx.close();
        }
    }

    private void directConnect(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg, Socks5AddressType socks5AddressType) {
        log.info("request address is {} port is {}", msg.dstAddr(), msg.dstPort());
        // ChannelFuture future;
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new OutboundResponse(ctx));
            }
        });
        clientBootstrap.connect(msg.dstAddr(), msg.dstPort())
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.debug("target(address of {} port of {}) connected", msg.dstAddr(), msg.dstPort());
                        ctx.pipeline().addLast(new OutboundRequest(future));
                        DefaultSocks5CommandResponse commandResponse = new DefaultSocks5CommandResponse(Socks5CommandStatus.SUCCESS, socks5AddressType);
                        ctx.writeAndFlush(commandResponse);
                        ctx.pipeline().remove(SocksCommandRequestInboundHandler.class);
                        ctx.pipeline().remove(Socks5CommandRequestDecoder.class);
                    } else {
                        log.error("target(address of {} port of {}) connect failed", msg.dstAddr(), msg.dstPort());
                        DefaultSocks5CommandResponse commandResponse = new DefaultSocks5CommandResponse(Socks5CommandStatus.FAILURE, socks5AddressType);
                        ctx.writeAndFlush(commandResponse);
                        future.channel().close();
                    }
                });
    }

    private void proxyConnect(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg, Socks5AddressType socks5AddressType) {
        log.info("request address is {} port is {}", msg.dstAddr(), msg.dstPort());
        // ChannelFuture future;
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new OutboundVMessResponse(ctx));
            }
        });
        clientBootstrap.connect(msg.dstAddr(), msg.dstPort())
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        log.debug("target(address of {} port of {}) connected", msg.dstAddr(), msg.dstPort());
                        ctx.pipeline().addLast(new OutboundVMessRequest(future));
                        DefaultSocks5CommandResponse commandResponse = new DefaultSocks5CommandResponse(Socks5CommandStatus.SUCCESS, socks5AddressType);
                        ctx.writeAndFlush(commandResponse);
                        ctx.pipeline().remove(SocksCommandRequestInboundHandler.class);
                        ctx.pipeline().remove(Socks5CommandRequestDecoder.class);
                    } else {
                        log.error("target(address of {} port of {}) connect failed", msg.dstAddr(), msg.dstPort());
                        DefaultSocks5CommandResponse commandResponse = new DefaultSocks5CommandResponse(Socks5CommandStatus.FAILURE, socks5AddressType);
                        ctx.writeAndFlush(commandResponse);
                        future.channel().close();
                    }
                });
    }
}
