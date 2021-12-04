package top.aziraphale.infra.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConfigWrapper {

    /**
     * 	LogConfig       *LogConfig             `json:"log"`
     * 	RouterConfig    *RouterConfig          `json:"routing"`
     * 	DNSConfig       *DNSConfig             `json:"dns"`
     * 	InboundConfigs  []InboundDetourConfig  `json:"inbounds"`
     * 	OutboundConfigs []OutboundDetourConfig `json:"outbounds"`
     */
    private LogConfig log;
    private RouterConfig routing;
    private List<InboundDetourConfig> inbounds;
    private List<OutboundDetourConfig> outbounds;

}
