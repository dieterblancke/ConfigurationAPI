package be.dieterblancke.configuration.json.bukkit;

import be.dieterblancke.configuration.json.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.lang.reflect.Type;
import java.util.Map;

public class BukkitJsonConfigurationDeserializer implements JsonDeserializer<ConfigurationSerializable>
{

    public ConfigurationSerializable deserialize( JsonElement element, Type type, JsonDeserializationContext context ) throws JsonParseException
    {
        final Map<String, Object> values = JsonUtils.readValues( "", element );

        if ( values.containsKey( "==" ) )
        {
            try
            {
                return ConfigurationSerialization.deserializeObject( values );
            }
            catch ( IllegalArgumentException e )
            {
                throw new JsonParseException( "Could not deserialize object", e );
            }
        }
        throw new JsonParseException( "Could not deserialize object" );
    }
}
