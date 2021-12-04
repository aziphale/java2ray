package top.aziraphale.infra.conf.condition;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.aziraphale.RayServerStarter;
import top.aziraphale.infra.conf.ConfigWrapper;

/**
 * @author Sheffery Shake
 * active inbound or outbound module
 * if configuration mentioned
 */
@Order
public class ConfExistCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /*if (CollectionUtils.isNotEmpty(RayServerStarter.CONFIG.getInbounds())) {
            metadata.isAnnotated()
        }*/

        return true;
    }
}
