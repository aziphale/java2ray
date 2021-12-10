package top.aziraphale.proxy.vmess.out;

import io.netty.channel.ChannelFuture;
import top.aziraphale.proxy.common.OutboundRequest;

public class OutboundVMessRequest extends OutboundRequest {

    public OutboundVMessRequest(ChannelFuture dstChannelFuture) {
        super(dstChannelFuture);
    }
}
