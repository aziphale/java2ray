package top.aziraphale.infra.conf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import top.aziraphale.infra.conf.condition.ConfExistCondition;

@Getter
@Setter
@ToString
public class InboundDetourConfig {

    private String protocol;

}
