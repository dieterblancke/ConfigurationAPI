package com.dbsoftwares.configuration.yaml;

import com.dbsoftwares.configuration.api.Utils;
import com.dbsoftwares.configuration.serialization.ConfigurationSerializable;
import com.dbsoftwares.configuration.serialization.ConfigurationSerialization;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Representer;

import java.util.LinkedHashMap;
import java.util.Map;

public class YamlRepresenter extends Representer {

    public YamlRepresenter() {
        this.multiRepresenters.put(YamlSection.class, new RepresentMap() {
            @Override
            public Node representData(Object object) {
                YamlSection section = (YamlSection) object;

                return super.representData(section.self);
            }
        });

        if (Utils.isBukkit()) {
            this.multiRepresenters.put(org.bukkit.configuration.serialization.ConfigurationSerializable.class, new RepresentMap() {
                @Override
                public Node representData(Object object) {
                    org.bukkit.configuration.serialization.ConfigurationSerializable serializable = (org.bukkit.configuration.serialization.ConfigurationSerializable) object;
                    Map<String, Object> values = new LinkedHashMap<>();
                    values.put("==", org.bukkit.configuration.serialization.ConfigurationSerialization.getAlias(serializable.getClass()));
                    values.putAll(serializable.serialize());
                    return super.representData(values);
                }
            });
        }
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentMap() {
            @Override
            public Node representData(Object object) {
                ConfigurationSerializable serializable = (ConfigurationSerializable) object;
                Map<String, Object> values = new LinkedHashMap<>();
                values.put("==", ConfigurationSerialization.getAlias(serializable.getClass()));
                values.putAll(serializable.serialize());
                return super.representData(values);
            }
        });
    }
}