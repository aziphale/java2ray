package top.aziraphale.proxy.socks.in;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socksx.v5.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import top.aziraphale.proxy.common.OutboundRequest;
import top.aziraphale.proxy.common.OutboundResponse;

@Slf4j
public class SocksCommandRequestInboundHandler extends SimpleChannelInboundHandler<DefaultSocks5CommandRequest> {

    private final Bootstrap clientBootstrap;

    public SocksCommandRequestInboundHandler(Bootstrap clientBootstrap) {
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg) throws Exception {
        Socks5AddressType socks5AddressType = msg.dstAddrType();
        if (!Socks5CommandType.CONNECT.equals(msg.type())) {
            log.debug("command type is {}", msg.type());
            ReferenceCountUtil.retain(msg);
            ctx.fireChannelRead(msg);
            return;
        }
        log.debug("target address is {} port is {}", msg.dstAddr(), msg.dstPort());
        directConnect(ctx, msg, socks5AddressType);
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
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
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
                    }
                });
    }

    private void proxyConnect(ChannelHandlerContext ctx, DefaultSocks5CommandRequest msg, Socks5AddressType socks5AddressType) {
        log.info("request address is {} port is {}", msg.dstAddr(), msg.dstPort());
        // ChannelFuture future;
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new OutboundResponse(ctx));
            }
        });
        clientBootstrap.connect(msg.dstAddr(), msg.dstPort())
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
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
                    }
                });
    }
}
