package com.dbsoftwares.configuration.json;

import com.dbsoftwares.configuration.serialization.ConfigurationSerializable;
import com.dbsoftwares.configuration.serialization.ConfigurationSerialization;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonConfigurationDeserializer implements JsonDeserializer<ConfigurationSerializable> {

    public ConfigurationSerializable deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        final Map<String, Object> values = JsonUtils.readValues("", element);

        if (values.containsKey("==")) {
            try {
                return ConfigurationSerialization.deserializeObject(values);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Could not deserialize object", e);
            }
        }
        throw new JsonParseException("Could not deserialize object");
    }
}
