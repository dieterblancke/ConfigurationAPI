package dev.endoy.configuration.json;

import dev.endoy.configuration.api.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils
{

    public static Map<String, Object> readValues( String prefix, JsonElement element )
    {
        final Map<String, Object> values = new LinkedHashMap<>();

        if ( element.isJsonPrimitive() )
        {
            final JsonPrimitive primitive = element.getAsJsonPrimitive();
            final Object value = getValue( primitive );

            values.put( prefix, value );
        }
        else if ( element.isJsonArray() )
        {
            final JsonArray array = element.getAsJsonArray();
            final List<Object> list = new ArrayList<>();

            for ( JsonElement e : array )
            {
                if ( e.isJsonPrimitive() )
                {
                    final Object value = getValue( e.getAsJsonPrimitive() );

                    list.add( value );
                }
                else if ( e.isJsonObject() )
                {
                    list.add( readValues( prefix, e.getAsJsonObject() ) );
                }
                else if ( e.isJsonArray() )
                {
                    list.add( readValues( prefix, e.getAsJsonArray() ) );
                }
            }

            values.put( prefix, list );
        }
        else if ( element.isJsonObject() )
        {
            final JsonObject obj = element.getAsJsonObject();

            for ( Map.Entry<String, JsonElement> entry : obj.entrySet() )
            {
                values.put( prefix, readValues( entry.getKey(), entry.getValue() ) );
            }
        }
        return values;
    }

    private static Object getValue( JsonPrimitive primitive )
    {
        Field field = Utils.getField( primitive.getClass(), "value" );
        try
        {
            return field.get( primitive );
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
            return null;
        }
    }
}
