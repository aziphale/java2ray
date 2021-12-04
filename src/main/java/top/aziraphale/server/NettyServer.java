package top.aziraphale.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {

    private final ServerBootstrap serverBootstrap;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyServer(ServerBootstrap serverBootstrap, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        this.serverBootstrap = serverBootstrap;
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
    }

    public void run() throws Exception {
        try {
            serverBootstrap.childHandler(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel channel) {
                    
                }
            });

            ChannelFuture future = serverBootstrap.bind(8080).sync();

            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
