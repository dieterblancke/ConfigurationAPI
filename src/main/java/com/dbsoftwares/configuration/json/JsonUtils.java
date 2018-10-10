package com.dbsoftwares.configuration.json;

import com.dbsoftwares.configuration.api.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static Map<String, Object> readValues(String prefix, JsonElement element) {
        final Map<String, Object> values = new LinkedHashMap<>();

        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            Object value = getValue(primitive);

            if (value instanceof BigDecimal) {
                values.put(prefix, primitive.getAsBigDecimal());
            } else if (value instanceof BigInteger) {
                values.put(prefix, primitive.getAsBigInteger());
            } else if (primitive.isBoolean()) {
                values.put(prefix, primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                values.put(prefix, primitive.getAsNumber());
            } else if (primitive.isString()) {
                values.put(prefix, primitive.getAsString());
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            List<Object> list = new ArrayList<>();

            for (JsonElement e : array) {
                if (!e.isJsonPrimitive()) {
                    if (e.isJsonObject()) {
                        list.add(readValues(prefix, e.getAsJsonObject()));
                    }
                    continue;
                }
                JsonPrimitive primitive = e.getAsJsonPrimitive();
                Object value = getValue(primitive);
                if (value instanceof BigDecimal) {
                    list.add(primitive.getAsBigDecimal());
                } else if (value instanceof BigInteger) {
                    list.add(primitive.getAsBigInteger());
                } else if (primitive.isBoolean()) {
                    list.add(primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    list.add(primitive.getAsNumber());
                } else if (primitive.isString()) {
                    list.add(primitive.getAsString());
                }
            }

            values.put(prefix, list);
        } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                values.put(prefix, readValues(entry.getKey(), entry.getValue()));
            }
        }
        return values;
    }

    private static Object getValue(JsonPrimitive primitive) {
        Field field = Utils.getField(primitive.getClass(), "value");
        try {
            return field.get(primitive);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
