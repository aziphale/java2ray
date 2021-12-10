package top.aziraphale.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import top.aziraphale.infra.conf.ConfigWrapper;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigJsonParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.enable(JsonParser.Feature.ALLOW_COMMENTS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static ConfigWrapper parse(Resource resource) throws IOException {
        return MAPPER.readValue(resource.getInputStream(), ConfigWrapper.class);
    }

}
