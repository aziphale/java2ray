package top.aziraphale.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v5.Socks5CommandRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;
import top.aziraphale.proxy.socks.in.SocksCommandRequestInboundHandler;
import top.aziraphale.proxy.socks.in.SocksInitialRequestInboundHandler;

/**
 * arrange pipeline in every channel
 * @author sheffery
 * @date 2021-12-13 7:45 PM
 */
public class ChannelBase extends ChannelInitializer {

    private final Bootstrap clientBootstrap;

    public ChannelBase(Bootstrap clientBootstrap) {
        this.clientBootstrap = clientBootstrap;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(Socks5ServerEncoder.DEFAULT);
        pipeline.addLast(new Socks5InitialRequestDecoder());
        pipeline.addLast(new SocksInitialRequestInboundHandler());
        pipeline.addLast(new Socks5CommandRequestDecoder());
        pipeline.addLast(new SocksCommandRequestInboundHandler(clientBootstrap));
    }
}
