package top.aziraphale.proxy.common.handler;

import java.util.List;

public interface RayOutHandler {

    Target pick();

    Target pick(String tag);

    List<Target> handlerList();

}
