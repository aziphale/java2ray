package top.aziraphale.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.aziraphale.proxy.socks.SocksCommandRequestInboundHandler;
import top.aziraphale.proxy.socks.SocksInitialRequestInboundHandler;

@Component
@Slf4j
public class NettyServer {

    private final Bootstrap clientBootstrap;
    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyServer(Bootstrap clientBootstrap, ServerBootstrap serverBootstrap, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        this.clientBootstrap = clientBootstrap;
        this.serverBootstrap = serverBootstrap;
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
    }

    public void run() throws Exception {
        try {
            serverBootstrap.childHandler(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel channel) {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(Socks5ServerEncoder.DEFAULT);
                    pipeline.addLast(new Socks5InitialRequestDecoder());
                    pipeline.addLast(new SocksInitialRequestInboundHandler());
//                    pipeline.addLast(new Socks5PasswordAuthRequestDecoder());
//                    pipeline.addLast(new SocksAuthRequestInboundHandler());
                    pipeline.addLast(new Socks5CommandRequestDecoder());
                    pipeline.addLast(new SocksCommandRequestInboundHandler(clientBootstrap));
                }
            });

            log.info("bind port {}", 8080);
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            log.info("bind port successfully, wait for connection coming");

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
