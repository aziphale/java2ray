package top.aziraphale.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import top.aziraphale.RayServerStarter;
import top.aziraphale.exception.NoneInboundException;
import top.aziraphale.infra.conf.InboundDetourConfig;

import java.util.List;

/**
 * core server start here
 * @author sheffery
 * @date 2021-12-13 7:45 PM
 */
@Slf4j
@Component
public class NettyServer implements DisposableBean {

    private final Bootstrap clientBootstrap;
    private final ServerBootstrap serverBootstrap;

    private final EventLoopGroup clientGroup;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyServer(Bootstrap clientBootstrap, ServerBootstrap serverBootstrap, EventLoopGroup clientGroup, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        this.clientBootstrap = clientBootstrap;
        this.serverBootstrap = serverBootstrap;
        this.clientGroup = clientGroup;
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
    }

    public void run() throws Exception {

        serverBootstrap.childHandler(new ChannelBase(this.clientBootstrap));

        // 监听端口
        List<InboundDetourConfig> inbounds = RayServerStarter.getConfig().getInbounds();
        if (CollectionUtils.isEmpty(inbounds)) {
            throw new NoneInboundException();
        }
        for (InboundDetourConfig inbound : inbounds) {
            log.info("bind port {}", inbound.getPort());
            serverBootstrap.bind(inbound.getPort()).sync();
            log.info("bind port successfully, {} wait for connection coming", inbound.getProtocol());
        }
    }

    @Override
    public void destroy() {
        clientGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.debug("shut down eventLoopGroup");
    }
}
