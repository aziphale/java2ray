package top.aziraphale.proxy.common.handler;

import java.util.List;

public interface RayOutHandler {

    Target pick();

    List<Target> handlerList();

}
