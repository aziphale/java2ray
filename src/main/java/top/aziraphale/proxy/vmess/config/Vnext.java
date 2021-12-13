package top.aziraphale.proxy.vmess.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Sheffery
 * @date 2021/12/13 9:30 PM
 */
@Getter
@Setter
public class Vnext {

    private String address;
    private Integer port;
    private List<Vuser> users;
}
