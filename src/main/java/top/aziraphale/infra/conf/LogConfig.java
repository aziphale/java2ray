package top.aziraphale.infra.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogConfig {

    /**
     * 	AccessLog string `json:"access"`
     * 	ErrorLog  string `json:"error"`
     * 	LogLevel  string `json:"loglevel"`
     */
    @JsonProperty("access")
    private String accessLog;
    private String error;
    @JsonProperty("loglevel")
    private String logLevel;

}
