package equix.tech.homework.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger("equix.Utils");

    public static String toJsonString(Object object) {
        if (Objects.nonNull(object)) {
            try {
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                log.error("[ERROR_CONVERT_OBJECT], exception message: {}", e.getMessage());
                return "{}";
            }
        }
        return "{}";
    }
}
