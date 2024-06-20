package dev.endoy.configuration;

import java.io.File;
import java.io.IOException;

public abstract class ConfigurationTest
{

    protected final File resourcesFolder;

    public ConfigurationTest()
    {
        resourcesFolder = new File( "src/test/resources" );
    }

    protected void createFileIfNotExists( final File file ) throws IOException
    {
        if ( !file.getParentFile().exists() )
        {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
    }
}
