package top.aziraphale.proxy.common.handler;

/**
 * outbound target information
 */
public interface Target {

    /**
     * acquire outbound tag
     * @return
     */
    String getTag();

    /**
     * may be ip or domain
     * acquire outbound address
     * @return
     */
    String getAddress();

    /**
     * acquire outbound port
     * @return
     */
    Integer getPort();
}
