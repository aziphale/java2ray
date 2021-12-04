package top.aziraphale.infra.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import top.aziraphale.infra.conf.condition.ConfExistCondition;

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
