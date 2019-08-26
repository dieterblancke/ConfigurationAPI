package com.dbsoftwares.configuration.json;

import com.dbsoftwares.configuration.api.ISection;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class JsonSectionSerializer implements JsonSerializer<ISection> {

    @Override
    public JsonElement serialize(ISection section, Type type, JsonSerializationContext context) {
        return context.serialize(section.getValues());
    }
}