package cz.krasnyt.vatchecker.data.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.function.Function;

/**
 * Base class for String deserializers (from String to type). Other classes may extend this class for
 * more specific deserialization.
 * @param <T> type of deserialized object
 */
public class StringDeserializer<T> extends StdDeserializer<T> {

    private final Class<T> clazz;
    private final Function<String, T> mapper;

    /**
     * Creates a new deserializer.
     * @param clazz class type this deserializer will be deserializing
     * @param mapper mapping function that converts String into the type of this deserializer
     */
    public StringDeserializer(Class<T> clazz, Function<String, T> mapper) {
        super(clazz);
        this.clazz = clazz;
        this.mapper = mapper;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return mapper.apply(p.getText());
    }

    @Override
    public String toString() {
        return "StringDeserializer{class=" + clazz + "}";
    }

}