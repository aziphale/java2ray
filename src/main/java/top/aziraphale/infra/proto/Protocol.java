package top.aziraphale.infra.proto;

import lombok.Getter;

/**
 * support tunnel protocol
 */
public enum Protocol {

    SOCK("sock"),
    HTTP("http"),
    VMESS("vmess");

    @Getter
    private final String name;

    Protocol(String name) {
        this.name = name;
    }

}
