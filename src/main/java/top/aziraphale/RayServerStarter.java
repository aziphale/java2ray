package top.aziraphale;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import top.aziraphale.infra.conf.ConfigWrapper;
import top.aziraphale.server.NettyServer;
import top.aziraphale.utils.ConfigJsonParser;

import java.io.IOException;

@Slf4j
@Component
@ComponentScan
public class RayServerStarter implements DisposableBean {

    /**
     * deserialized config
     */
    @Getter
    private static ConfigWrapper config;

    // run status
    private static boolean running = true;

    public static void main(String[] args) {

        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:config.json");

        try {
            config = ConfigJsonParser.parse(resource);
            log.trace(config.toString());
        } catch (IOException e) {
            log.error("There is no configuration file");
            return;
        }

        // init configuration beans
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RayServerStarter.class);

        NettyServer nettyServer = context.getBean(NettyServer.class);
        try {
            nettyServer.run();
        } catch (Exception exception) {
            log.error("core server start failed", exception);
        }

        context.registerShutdownHook();
        synchronized (RayServerStarter.class) {
            while (running) {
                try {
                    RayServerStarter.class.wait();
                } catch (InterruptedException interruptedException) {
                    log.error("netty thread error", interruptedException);
                }
            }
        }
    }

    @Override
    public void destroy() {
        synchronized (RayServerStarter.class) {
            running = false;
            RayServerStarter.class.notify();
        }
    }
}