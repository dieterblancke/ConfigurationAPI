package com.dbsoftwares.configuration.json;

import com.dbsoftwares.configuration.api.IConfiguration;
import com.dbsoftwares.configuration.api.Utils;
import com.dbsoftwares.configuration.json.bukkit.BukkitJsonConfigurationDeserializer;
import com.dbsoftwares.configuration.json.bukkit.BukkitJsonConfigurationSerializer;
import com.dbsoftwares.configuration.serialization.ConfigurationSerializable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonConfiguration extends JsonSection implements IConfiguration {

    private File file;
    private Gson gson = createGson();

    public JsonConfiguration(File file) throws IOException {
        this(new FileInputStream(file));
        this.file = file;
    }

    @SuppressWarnings("unchecked")
    public JsonConfiguration(final InputStream input) throws IOException {
        if (input == null) {
            return;
        }

        try (final InputStream inputStream = input;
             final InputStreamReader reader = new InputStreamReader(inputStream)) {
            Map<String, Object> values = gson.fromJson(reader, Map.class);

            if (values == null) {
                values = new LinkedHashMap<>();
            }

            loadIntoSections(values, this);
        }
    }

    private static Gson createGson() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
                .setExclusionStrategies(new JsonExclusionStrategy()); // usually fields of superclasses are not what we want

        builder.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new JsonConfigurationSerializer())
                .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new JsonConfigurationDeserializer());

        if (Utils.isBukkit()) {
            Class<?> configurationSerializable = Utils.getClass("org.bukkit.configuration.serialization.ConfigurationSerializable");

            builder.registerTypeHierarchyAdapter(configurationSerializable, new BukkitJsonConfigurationSerializer())
                    .registerTypeHierarchyAdapter(configurationSerializable, new BukkitJsonConfigurationDeserializer());
        }

        return builder.create();
    }

    @Override
    public void copyDefaults(IConfiguration config) throws IOException {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        boolean changed = false;
        for (String key : config.getKeys()) {
            if (!exists(key)) {
                set(key, config.get(key));
                changed = true;
            }
        }
        if (changed) {
            save();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void reload() throws IOException {
        if (file == null) {
            return;
        }
        values.clear();

        try (final FileInputStream input = new FileInputStream(file);
             final InputStreamReader reader = new InputStreamReader(input)) {
            Map<String, Object> values = gson.fromJson(reader, Map.class);

            if (values == null) {
                values = new LinkedHashMap<>();
            }

            loadIntoSections(values, this);
        }
    }

    @Override
    public void save() throws IOException {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(gson.toJson(values));
            writer.flush();
        }
    }
}