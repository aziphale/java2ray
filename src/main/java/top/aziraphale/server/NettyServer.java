package top.aziraphale.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.aziraphale.RayServerStarter;
import top.aziraphale.exception.NoneInboundException;
import top.aziraphale.infra.conf.InboundDetourConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * core server start here
 * @author sheffery
 * @date 2021-12-13 7:45 PM
 */
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
            serverBootstrap.childHandler(new ChannelBase(this.clientBootstrap));

            List<InboundDetourConfig> inbounds = RayServerStarter.getConfig().getInbounds();

            if (CollectionUtils.isEmpty(inbounds)) {
                throw new NoneInboundException();
            }

            List<ChannelFuture> futures = new ArrayList<>();
            for (InboundDetourConfig inbound : inbounds) {
                log.info("bind port {}", inbound.getPort());
                ChannelFuture future = serverBootstrap.bind(inbound.getPort()).sync();
                futures.add(future);
                log.info("bind port successfully, {} wait for connection coming", inbound.getProtocol());
            }

            for (ChannelFuture future : futures) {
                future.channel().closeFuture().sync();
            }
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
