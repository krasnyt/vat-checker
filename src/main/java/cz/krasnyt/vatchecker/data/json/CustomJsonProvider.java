package cz.krasnyt.vatchecker.data.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import java.time.LocalDate;

/**
 * Custom Jackson Json provider that registers a custom ObjectMapper that is already configured
 * including custom deserializers.
 */
public final class CustomJsonProvider extends JacksonJaxbJsonProvider {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule customModule = new SimpleModule();

        // this way we can easily add other deserializers
        customModule.addDeserializer(LocalDate.class,
                new StringDeserializer<>(LocalDate.class, LocalDate::parse));

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(customModule);
    }

    public CustomJsonProvider() {
        super();
        setMapper(mapper);
    }

}