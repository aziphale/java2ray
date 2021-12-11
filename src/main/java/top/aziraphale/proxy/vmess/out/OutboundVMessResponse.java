package top.aziraphale.proxy.vmess.out;

import io.netty.channel.ChannelHandlerContext;
import top.aziraphale.proxy.common.OutboundResponse;

public class OutboundVMessResponse extends OutboundResponse {

    public OutboundVMessResponse(ChannelHandlerContext clientChannelHandlerContext) {
        super(clientChannelHandlerContext);
    }
}
