package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.InputStream;

public class JSONHelper {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        try {
            SimpleModule module = new SimpleModule("JodaSerialiser");
            ObjectMapper objectMapper = new ObjectMapper()
                    .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                    .enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(module);
            OBJECT_MAPPER = objectMapper;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create JSON ObjectMapper instance", e);
        }
    }

    public static String marshal(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize JSON message", e);
        }
    }

    public static <T> T unmarshal(InputStream data) throws Exception {
        return OBJECT_MAPPER.readValue(data, new TypeReference<T>() {
        });
    }

    public static <T> T unmarshalResource(String jsonDocument) throws Exception {
        return unmarshal(JSONHelper.class.getResourceAsStream(jsonDocument));
    }

    public static <T> T unmarshalFile(File data) throws Exception {
        return OBJECT_MAPPER.readValue(data, new TypeReference<T>() {
        });
    }

}