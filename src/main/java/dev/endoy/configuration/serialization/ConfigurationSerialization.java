package dev.endoy.configuration.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationSerialization
{

    private static final Map<String, Class<? extends ConfigurationSerializable>> aliases = new LinkedHashMap<>();
    private final Class<? extends ConfigurationSerializable> clazz;

    private ConfigurationSerialization( Class<? extends ConfigurationSerializable> clazz )
    {
        this.clazz = clazz;
    }

    public static ConfigurationSerializable deserializeObject( Map<String, ?> args )
    {
        Class<? extends ConfigurationSerializable> clazz;

        if ( args.containsKey( "==" ) )
        {
            try
            {
                String alias = (String) args.get( "==" );

                if ( alias == null )
                {
                    throw new IllegalArgumentException( "Cannot have null alias" );
                }
                clazz = getClassByAlias( alias );
                if ( clazz == null )
                {
                    throw new IllegalArgumentException( "Specified class does not exist ('" + alias + "')" );
                }
            }
            catch ( ClassCastException ex )
            {
                ex.fillInStackTrace();
                throw ex;
            }
        }
        else
        {
            throw new IllegalArgumentException( "Args doesn't contain type key ('==')" );
        }

        return new ConfigurationSerialization( clazz ).deserialize( args );
    }

    /**
     * Registers the given {@link ConfigurationSerializable} class by its alias
     *
     * @param clazz Class to register
     */
    public static void registerClass( Class<? extends ConfigurationSerializable> clazz )
    {
        registerClass( clazz, getAlias( clazz ) );
        registerClass( clazz, clazz.getName() );
    }

    /**
     * Registers the given alias to the specified {@link ConfigurationSerializable} class
     *
     * @param clazz Class to register
     * @param alias Alias to register as
     * @see SerializableAs
     */
    public static void registerClass( Class<? extends ConfigurationSerializable> clazz, String alias )
    {
        aliases.put( alias, clazz );
    }

    /**
     * Unregisters the specified alias to a {@link ConfigurationSerializable}
     *
     * @param alias Alias to unregister
     */
    public static void unregisterClass( String alias )
    {
        aliases.remove( alias );
    }

    /**
     * Unregisters any aliases for the specified {@link ConfigurationSerializable} class
     *
     * @param clazz Class to unregister
     */
    public static void unregisterClass( Class<? extends ConfigurationSerializable> clazz )
    {
        while ( aliases.values().remove( clazz ) )
        {
        }
    }

    /**
     * Attempts to get a registered {@link ConfigurationSerializable} class by its alias
     *
     * @param alias Alias of the serializable
     * @return Registered class, or null if not found
     */
    public static Class<? extends ConfigurationSerializable> getClassByAlias( String alias )
    {
        return aliases.get( alias );
    }

    /**
     * Gets the correct alias for the given {@link ConfigurationSerializable}
     * class
     *
     * @param clazz Class to get alias for
     * @return Alias to use for the class
     */
    public static String getAlias( Class<? extends ConfigurationSerializable> clazz )
    {
        SerializableAs alias = clazz.getAnnotation( SerializableAs.class );

        if ( ( alias != null ) && ( alias.value() != null ) )
        {
            return alias.value();
        }

        return clazz.getName();
    }

    private Method getMethod( String name, boolean isStatic )
    {
        try
        {
            Method method = clazz.getDeclaredMethod( name, Map.class );

            if ( !ConfigurationSerializable.class.isAssignableFrom( method.getReturnType() ) )
            {
                return null;
            }
            if ( Modifier.isStatic( method.getModifiers() ) != isStatic )
            {
                return null;
            }

            return method;
        }
        catch ( NoSuchMethodException | SecurityException ex )
        {
            return null;
        }
    }

    private ConfigurationSerializable deserializeViaMethod( Method method, Map<String, ?> args )
    {
        try
        {
            ConfigurationSerializable result = (ConfigurationSerializable) method.invoke( null, args );

            if ( result == null )
            {
                Logger.getLogger( ConfigurationSerialization.class.getName() ).log( Level.SEVERE, "Could not call method '" + method + "' of " + clazz + " for deserialization: method returned null" );
            }
            else
            {
                return result;
            }
        }
        catch ( Throwable ex )
        {
            Logger.getLogger( ConfigurationSerialization.class.getName() ).log(
                    Level.SEVERE,
                    "Could not call method '" + method.toString() + "' of " + clazz + " for deserialization",
                    ex instanceof InvocationTargetException ? ex.getCause() : ex );
        }

        return null;
    }

    private ConfigurationSerializable deserialize( Map<String, ?> args )
    {
        ConfigurationSerializable result = null;
        Method method;

        if ( result == null )
        {
            method = getMethod( "deserialize", true );

            if ( method != null )
            {
                result = deserializeViaMethod( method, args );
            }
        }

        return result;
    }
}
