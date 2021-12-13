package top.aziraphale.proxy.vmess.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sheffery
 * @date 2021/12/13 9:30 PM
 */
@Getter
@Setter
public class Vuser {

    private String id;
    private String encryption;
    private Integer level;
}
