package com.dbsoftwares.configuration.yaml;

import com.dbsoftwares.configuration.api.Utils;
import com.dbsoftwares.configuration.serialization.ConfigurationSerialization;
import com.google.common.collect.Maps;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Map;

public class YamlConstructor extends SafeConstructor {

    public YamlConstructor() {
        this.yamlConstructors.put(Tag.MAP, new CustomObjectConstructor());
    }

    private class CustomObjectConstructor extends ConstructYamlMap {

        private CustomObjectConstructor() {
            super();
        }

        @Override
        public Object construct(Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected mapping structure, node: " + node);
            } else {
                Map<?, ?> raw = (Map) super.construct(node);

                if (raw.containsKey("==")) {
                    Map<String, Object> result = Maps.newLinkedHashMapWithExpectedSize(raw.size());
                    raw.forEach((key, value) -> result.put(key.toString(), value));

                    if (Utils.isBukkit()) {
                        try {
                            return deserialize(result, true);
                        } catch (IllegalArgumentException e) {
                            try {
                                return deserialize(result, false);
                            } catch (IllegalArgumentException ex) {
                                throw new YAMLException("Could not deserialize object", ex);
                            }
                        }
                    }
                    try {
                        return deserialize(result, false);
                    } catch (IllegalArgumentException e) {
                        throw new YAMLException("Could not deserialize object", e);
                    }
                } else {
                    return raw;
                }
            }
        }

        private Object deserialize(Map<String, Object> result, boolean bukkit) {
            if (bukkit) {
                return org.bukkit.configuration.serialization.ConfigurationSerialization.deserializeObject(result);
            }
            return ConfigurationSerialization.deserializeObject(result);

        }

        @Override
        public void construct2ndStep(Node node, Object object) {
            throw new YAMLException("Unexpected mapping structure, node: " + node);
        }
    }
}
