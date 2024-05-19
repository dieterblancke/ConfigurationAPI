package dev.endoy.configuration.json.bukkit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class BukkitJsonConfigurationSerializer implements JsonSerializer<ConfigurationSerializable>
{

    private final Gson gson = new Gson();

    @Override
    public JsonElement serialize( ConfigurationSerializable serializable, Type type, JsonSerializationContext context )
    {
        return gson.toJsonTree( createSerializedObjectMap( serializable ) );
    }

    private Map<String, Object> createSerializedObjectMap( final ConfigurationSerializable serializable )
    {
        final Map<String, Object> map = new LinkedHashMap<>();

        map.put( "==", ConfigurationSerialization.getAlias( serializable.getClass() ) );

        serializable.serialize().forEach( ( key, value ) ->
        {
            if ( value instanceof ConfigurationSerializable )
            {
                map.put( key, createSerializedObjectMap( (ConfigurationSerializable) value ) );
            }
            else
            {
                map.put( key, value );
            }
        } );

        return map;
    }
}