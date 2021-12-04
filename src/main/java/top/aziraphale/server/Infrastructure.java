package top.aziraphale.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Infrastructure {

    @Bean
    public ServerBootstrap serverBootstrap() {
        return new ServerBootstrap().group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 256)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Bean
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

}