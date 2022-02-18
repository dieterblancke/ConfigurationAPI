package be.dieterblancke.configuration.json;

import be.dieterblancke.configuration.api.ConfigurationOptions;
import be.dieterblancke.configuration.api.IConfiguration;
import be.dieterblancke.configuration.api.Utils;
import be.dieterblancke.configuration.json.bukkit.BukkitJsonConfigurationSerializer;
import be.dieterblancke.configuration.serialization.ConfigurationSerializable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonConfiguration extends JsonSection implements IConfiguration
{

    private final Gson gson = createGson();
    private final JsonConfigurationOptions options;
    private File file;

    public JsonConfiguration( File file ) throws IOException
    {
        this( file, new JsonConfigurationOptions() );
    }

    public JsonConfiguration( File file, ConfigurationOptions configurationOptions ) throws IOException
    {
        this( new FileInputStream( file ), configurationOptions );
        this.file = file;
    }

    public JsonConfiguration( final InputStream inputStream ) throws IOException
    {
        this( inputStream, new JsonConfigurationOptions() );
    }

    public JsonConfiguration( final InputStream input, final ConfigurationOptions configurationOptions ) throws IOException
    {
        this.options = (JsonConfigurationOptions) configurationOptions;
        if ( input == null )
        {
            return;
        }

        try ( final InputStream inputStream = input;
              final InputStreamReader reader = new InputStreamReader( inputStream ) )
        {
            Map<String, Object> values = gson.fromJson(
                    reader, new TypeToken<HashMap<String, Object>>()
                    {
                    }.getType()
            );
            if ( values == null )
            {
                values = new LinkedHashMap<>();
            }

            loadIntoSections( values, this );
        }
    }

    private static Gson createGson()
    {
        final GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setExclusionStrategies( new JsonExclusionStrategy() ) // usually fields of superclasses are not what we want
                .registerTypeHierarchyAdapter( JsonSection.class, new JsonSectionSerializer() )
                .registerTypeHierarchyAdapter( ConfigurationSerializable.class, new JsonConfigurationSerializer() );

        if ( Utils.isBukkit() )
        {
            final Class<?> configurationSerializable = Utils.getClass( "org.bukkit.configuration.serialization.ConfigurationSerializable" );

            builder.registerTypeHierarchyAdapter( configurationSerializable, new BukkitJsonConfigurationSerializer() );
        }

        return builder.create();
    }

    @Override
    public void copyDefaults( IConfiguration config ) throws IOException
    {
        if ( file == null )
        {
            return;
        }
        if ( !file.exists() )
        {
            file.createNewFile();
        }
        boolean changed = false;
        for ( String key : config.getKeys( true ) )
        {
            if ( !exists( key ) )
            {
                set( key, config.get( key ) );
                changed = true;
            }
        }
        if ( changed )
        {
            save();
        }
    }

    @Override
    public void reload() throws IOException
    {
        if ( file == null )
        {
            return;
        }
        values.clear();

        try ( final FileInputStream input = new FileInputStream( file );
              final InputStreamReader reader = new InputStreamReader( input ) )
        {
            Map<String, Object> values = gson.fromJson(
                    reader, new TypeToken<HashMap<String, Object>>()
                    {
                    }.getType()
            );

            if ( values == null )
            {
                values = new LinkedHashMap<>();
            }

            loadIntoSections( values, this );
        }
    }

    @Override
    public void save() throws IOException
    {
        try ( FileWriter fileWriter = new FileWriter( file );
              BufferedWriter writer = new BufferedWriter( fileWriter ) )
        {
            writer.write( gson.toJson( values ) );
            writer.flush();
        }
    }
}