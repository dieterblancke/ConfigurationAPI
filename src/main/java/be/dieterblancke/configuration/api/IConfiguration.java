package be.dieterblancke.configuration.api;

import be.dieterblancke.configuration.json.JsonConfigurationOptions;
import be.dieterblancke.configuration.yaml.YamlConfigurationOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface IConfiguration extends ISection
{

    /**
     * Creates a file from inputstream.
     *
     * @param input      The input stream to be used for file contents.
     * @param targetFile The file to be created.
     * @return The created file.
     */
    static File createDefaultFile( InputStream input, File targetFile )
    {
        if ( targetFile.exists() )
        {
            throw new RuntimeException( "Failed to create an already existing file!" );
        }
        if ( targetFile.isDirectory() )
        {
            throw new RuntimeException( "Failed to create a file from default!" );
        }
        if ( input == null )
        {
            throw new RuntimeException( "Cannot create a default file from a null value!" );
        }
        if ( !targetFile.getParentFile().exists() )
        {
            targetFile.getParentFile().mkdirs();
        }
        try
        {
            Files.copy( input, Paths.get( targetFile.toURI() ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return targetFile;
    }

    /**
     * Loads in a IConfiguration object from file.
     *
     * @param file The file that has to be read.
     * @return A new IConfiguration instance for the given file.
     */
    static <T extends IConfiguration> T loadJsonConfiguration( File file )
    {
        return loadConfiguration( FileStorageType.JSON, file );
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param input The stream that has to be read.
     * @return A new IConfiguration instance for the given stream.
     */
    static <T extends IConfiguration> T loadJsonConfiguration( InputStream input )
    {
        return loadConfiguration( FileStorageType.JSON, input );
    }

    /**
     * Loads in a IConfiguration object from file.
     *
     * @param file                 The file that has to be read.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance for the given file.
     */
    static <T extends IConfiguration> T loadJsonConfiguration( File file, JsonConfigurationOptions configurationOptions )
    {
        return loadConfiguration( FileStorageType.JSON, file, configurationOptions );
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param input                The stream that has to be read.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance for the given stream.
     */
    static <T extends IConfiguration> T loadJsonConfiguration( InputStream input, JsonConfigurationOptions configurationOptions )
    {
        return loadConfiguration( FileStorageType.JSON, input, configurationOptions );
    }

    /**
     * Loads in a IConfiguration object from file.
     *
     * @param file The file that has to be read.
     * @return A new IConfiguration instance for the given file.
     */
    static <T extends IConfiguration> T loadYamlConfiguration( File file )
    {
        return loadConfiguration( FileStorageType.YAML, file );
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param input The stream that has to be read.
     * @return A new IConfiguration instance for the given stream.
     */
    static <T extends IConfiguration> T loadYamlConfiguration( InputStream input )
    {
        return loadConfiguration( FileStorageType.YAML, input );
    }

    /**
     * Loads in a IConfiguration object from file.
     *
     * @param file                 The file that has to be read.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance for the given file.
     */
    static <T extends IConfiguration> T loadYamlConfiguration( File file, YamlConfigurationOptions configurationOptions )
    {
        return loadConfiguration( FileStorageType.YAML, file, configurationOptions );
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param input                The stream that has to be read.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance for the given stream.
     */
    static <T extends IConfiguration> T loadYamlConfiguration( InputStream input, YamlConfigurationOptions configurationOptions )
    {
        return loadConfiguration( FileStorageType.YAML, input, configurationOptions );
    }

    /**
     * Loads in a IConfiguration object from File.
     *
     * @param type JSON or YAML type.
     * @param file The file you want to load.
     * @return A new IConfiguration instance, null if an error occured.
     */
    static <T extends IConfiguration> T loadConfiguration( FileStorageType type, File file )
    {
        try
        {
            Constructor<?> constructor = Utils.getConstructor( getConfigurationClass( type ), File.class );
            return (T) constructor.newInstance( file );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param type   JSON or YAML type.
     * @param stream The InputStream you want to load.
     * @return A new IConfiguration instance, null if an error occured.
     */
    static <T extends IConfiguration> T loadConfiguration( FileStorageType type, InputStream stream )
    {
        try
        {
            Constructor<?> constructor = Utils.getConstructor( getConfigurationClass( type ), InputStream.class );
            return (T) constructor.newInstance( stream );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads in a IConfiguration object from File.
     *
     * @param type                 JSON or YAML type.
     * @param file                 The file you want to load.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance, null if an error occured.
     */
    static <T extends IConfiguration> T loadConfiguration( FileStorageType type, File file, ConfigurationOptions configurationOptions )
    {
        try
        {
            Constructor<?> constructor = Utils.getConstructor( getConfigurationClass( type ), File.class, ConfigurationOptions.class );
            return (T) constructor.newInstance( file, configurationOptions );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads in a IConfiguration object from InputStream.
     *
     * @param type                 JSON or YAML type.
     * @param stream               The InputStream you want to load.
     * @param configurationOptions The options to be used to load the configuration.
     * @return A new IConfiguration instance, null if an error occured.
     */
    static <T extends IConfiguration> T loadConfiguration( FileStorageType type, InputStream stream, ConfigurationOptions configurationOptions )
    {
        try
        {
            Constructor<?> constructor = Utils.getConstructor( getConfigurationClass( type ), InputStream.class, ConfigurationOptions.class );
            return (T) constructor.newInstance( stream, constructor );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the default (built in) class for Json and Yaml file management.
     *
     * @param type JSON or YAML type.
     * @return The default BungeeUtilisals management for these classes.
     */
    static Class<?> getConfigurationClass( FileStorageType type )
    {
        switch ( type )
        {
            default:
            case JSON:
                return Utils.getClass( "be.dieterblancke.configuration.json.JsonConfiguration" );
            case YAML:
                return Utils.getClass( "be.dieterblancke.configuration.yaml.YamlConfiguration" );
        }
    }

    /**
     * Copies keys and values from the given IConfiguration instance IF NOT found in the instance.
     *
     * @param configuration The configuration you want to load defaults from.
     * @throws IOException If there is an error saving the file.
     */
    void copyDefaults( IConfiguration configuration ) throws IOException;

    /**
     * Reloads the IConfiguration from File.
     *
     * @throws IOException Being thrown if the File is not found. For example if you reload a IConfiguration built with a stream.
     */
    void reload() throws IOException;

    /**
     * Saves the IConfiguration to the File.
     *
     * @throws IOException Being thrown if the File is not found. For example if you try to save a IConfiguration built with a stream.
     */
    void save() throws IOException;
}