package be.dieterblancke.configuration.yaml;

import be.dieterblancke.configuration.api.ConfigurationOptions;
import be.dieterblancke.configuration.api.IConfiguration;
import be.dieterblancke.configuration.yaml.comments.CommentType;
import be.dieterblancke.configuration.yaml.comments.YamlCommentDumper;
import be.dieterblancke.configuration.yaml.comments.YamlCommentMapper;
import be.dieterblancke.configuration.yaml.comments.YamlCommentParser;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlConfiguration extends YamlSection implements IConfiguration
{

    private final YamlConstructor constructor = new YamlConstructor();
    private final YamlRepresenter representer = new YamlRepresenter();
    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial( () ->
    {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );
        options.setAllowUnicode( true );
        return new Yaml( constructor, representer, options );
    } );
    private final YamlConfigurationOptions options;
    private YamlCommentMapper yamlCommentMapper;

    private File file;

    public YamlConfiguration( final File file ) throws IOException
    {
        this( file, YamlConfigurationOptions.builder().build() );
    }

    public YamlConfiguration( final File file, final ConfigurationOptions options ) throws IOException
    {
        this( file.exists() ? new FileInputStream( file ) : null, options );
        this.file = file;
    }

    public YamlConfiguration( final InputStream inputStream ) throws IOException
    {
        this( inputStream, YamlConfigurationOptions.builder().build() );
    }

    public YamlConfiguration( final InputStream input, final ConfigurationOptions options ) throws IOException
    {
        this.options = (YamlConfigurationOptions) options;

        if ( input == null )
        {
            return;
        }

        try ( final InputStream inputStream = input;
              final InputStreamReader reader = new InputStreamReader( inputStream ) )
        {
            final String content = CharStreams.toString( reader );
            Map<String, Object> values = yaml.get().load( content );

            if ( values == null )
            {
                values = new LinkedHashMap<>();
            }

            loadIntoSections( values, this );

            if ( this.options.isUseComments() )
            {
                this.parseComments( content );
            }
        }
    }

    @Override
    public void copyDefaults( IConfiguration configuration ) throws IOException
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
        for ( String key : configuration.getKeys( true ) )
        {
            if ( !exists( key ) )
            {
                Object value = configuration.get( key );

                set( key, value );

                if ( configuration instanceof YamlConfiguration )
                {
                    for ( CommentType commentType : CommentType.values() )
                    {
                        final String comment = ( (YamlConfiguration) configuration ).getComment( key, commentType );

                        if ( comment != null && !comment.isEmpty() )
                        {
                            setComment( key, comment, commentType );
                        }
                    }
                }

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
        self.clear();

        try ( final FileInputStream input = new FileInputStream( file );
              final InputStreamReader reader = new InputStreamReader( input ) )
        {
            final String content = CharStreams.toString( reader );
            Map<String, Object> values = yaml.get().load( content );

            if ( values == null )
            {
                values = new LinkedHashMap<>();
            }

            loadIntoSections( values, this );
            if ( options.isUseComments() )
            {
                this.parseComments( content );
            }
        }
    }

    @Override
    public void save() throws IOException
    {
        try ( FileWriter fileWriter = new FileWriter( file );
              BufferedWriter writer = new BufferedWriter( fileWriter ) )
        {
            writer.write( this.saveToString() );
        }
    }

    public String saveToString()
    {
        if ( options.isUseComments() )
        {
            return this.dumpWithComments();
        }
        else
        {
            return this.dumpWithoutComments();
        }
    }

    @SneakyThrows
    private String dumpWithComments()
    {
        return new YamlCommentDumper( this.parseComments(), new StringReader( this.dumpWithoutComments() ) ).dump();
    }

    private String dumpWithoutComments()
    {
        return this.yaml.get().dump( self );
    }

    @SneakyThrows
    public String fileToString()
    {
        if ( !file.exists() )
        {
            return null;
        }
        return new String( Files.readAllBytes( this.file.toPath() ) );
    }

    public void setComment( final String path, final String comment, final CommentType type )
    {
        if ( this.options.isUseComments() )
        {
            this.yamlCommentMapper.setComment( path, comment, type );
        }
    }

    public String getComment( final String path, final CommentType type )
    {
        return this.options.isUseComments() && this.yamlCommentMapper != null ? this.yamlCommentMapper.getComment( path, type ) : null;
    }


    private YamlCommentMapper parseComments()
    {
        if ( this.yamlCommentMapper != null )
        {
            return this.yamlCommentMapper;
        }
        return parseComments( fileToString() );
    }

    @SneakyThrows
    private YamlCommentMapper parseComments( final String contents )
    {
        if ( contents != null )
        {
            this.yamlCommentMapper = new YamlCommentParser( new StringReader( contents ) );
            ( (YamlCommentParser) this.yamlCommentMapper ).parse();
        }
        else
        {
            this.yamlCommentMapper = new YamlCommentMapper();
        }
        return this.yamlCommentMapper;
    }

    public YamlConfigurationOptions getOptions()
    {
        return options;
    }
}