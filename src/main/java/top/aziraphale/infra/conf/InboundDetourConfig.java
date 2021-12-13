package top.aziraphale.infra.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import top.aziraphale.infra.conf.condition.ConfExistCondition;

/**
 * all inbound configurations
 * <blockquote><pre>
 * {
 *     "listen": "127.0.0.1",
 *     "port": 1080,
 *     "protocol": "",
 *     "settings": {},
 *     "streamSettings": {},
 *     "tag": "",
 *     "sniffing": {
 *         "enabled": true,
 *         "destOverride": [
 *             "http",
 *             "tls"
 *         ],
 *         "metadataOnly": false
 *     },
 *     "allocate": {
 *         "strategy": "always",
 *         "refresh": 5,
 *         "concurrency": 3
 *     }
 * }
 * </pre></blockquote>
 */
@Getter
@Setter
@ToString
public class InboundDetourConfig {

    private String listen;
    private Integer port;
    private String protocol;
    private String tag;
}
