package dev.endoy.configuration.yaml.comments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YamlCommentReader extends YamlCommentMapper
{

    private static final Pattern KEY_REGEX = Pattern.compile( "^([ \\t-]*)([^#]*?)[ \\t]*:.*" );
    private static final Pattern ELEMENT_REGEX = Pattern.compile( "^([ \\t-]*)([^#\\n]*[^#\\s-]+).*" );
    private static final Pattern OTHER_REGEX = Pattern.compile( "^([ \\t-]*).*" );
    protected BufferedReader reader;
    protected String currentLine;
    protected String trim;

    protected YamlCommentReader( final Reader reader )
    {
        this.reader = new BufferedReader( reader );
    }

    protected boolean isBlank()
    {
        return this.trim.isEmpty();
    }

    protected boolean isComment()
    {
        return this.trim.startsWith( "#" );
    }

    protected boolean nextLine() throws IOException
    {
        this.currentLine = this.reader.readLine();
        if ( this.currentLine != null )
        {
            this.trim = this.currentLine.trim();
            return true;
        }
        return false;
    }

    protected KeyTree.Node track()
    {
        int indent = 0;
        String key = null;
        if ( this.currentLine != null )
        {
            final MatchResult groups = this.match( this.currentLine );
            indent = groups.group( 1 ).length();
            if ( groups.groupCount() > 1 )
            {
                key = groups.group( 2 );
            }
        }
        final KeyTree.Node parent = this.keyTree.findParent( indent );
        return parent.add( indent, key );
    }

    private MatchResult match( final String s )
    {
        Matcher matcher = YamlCommentReader.KEY_REGEX.matcher( s ); // for comments of section keys
        if ( matcher.matches() )
        {
            return matcher.toMatchResult();
        }
        matcher = YamlCommentReader.ELEMENT_REGEX.matcher( s ); // for comments of section values
        if ( matcher.matches() )
        {
            return matcher.toMatchResult();
        }
        matcher = YamlCommentReader.OTHER_REGEX.matcher( s ); // for anything else
        if ( matcher.matches() )
        {
            return matcher.toMatchResult();
        }
        throw new IllegalStateException( s + " cannot be matched" );
    }
}
