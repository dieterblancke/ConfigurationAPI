package dev.endoy.configuration.yaml;

import dev.endoy.configuration.api.ConfigurationOptions;
import lombok.Builder;

@Builder
public class YamlConfigurationOptions implements ConfigurationOptions
{

    private boolean useComments;

    public boolean isUseComments()
    {
        return useComments;
    }

    public void setUseComments( boolean useComments )
    {
        this.useComments = useComments;
    }
}
