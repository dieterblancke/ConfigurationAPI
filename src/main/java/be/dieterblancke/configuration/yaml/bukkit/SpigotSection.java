package be.dieterblancke.configuration.yaml.bukkit;

import be.dieterblancke.configuration.api.ISection;
import be.dieterblancke.configuration.api.ISpigotSection;
import be.dieterblancke.configuration.json.JsonSection;
import be.dieterblancke.configuration.yaml.YamlSection;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class SpigotSection implements ISpigotSection
{

    private final ISection parent;

    public SpigotSection( ISection parent )
    {
        this.parent = parent;
    }

    @Override
    public boolean isVector( String path )
    {
        Object object = parent.get( path );
        return object instanceof Vector;
    }

    @Override
    public Vector getVector( String path )
    {
        return parent.get( path );
    }

    @Override
    public Vector getVector( String path, Vector def )
    {
        Vector result = getVector( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isOfflinePlayer( String path )
    {
        Object object = parent.get( path );
        return object instanceof OfflinePlayer;
    }

    @Override
    public OfflinePlayer getOfflinePlayer( String path )
    {
        return parent.get( path );
    }

    @Override
    public OfflinePlayer getOfflinePlayer( String path, OfflinePlayer def )
    {
        OfflinePlayer result = getOfflinePlayer( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isItemStack( String path )
    {
        Object object = parent.get( path );
        return object instanceof ItemStack;
    }

    @Override
    public ItemStack getItemStack( String path )
    {
        return parent.get( path );
    }

    @Override
    public ItemStack getItemStack( String path, ItemStack def )
    {
        ItemStack result = getItemStack( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isColor( String path )
    {
        Object object = parent.get( path );
        return object instanceof Color;
    }

    @Override
    public Color getColor( String path )
    {
        return parent.get( path );
    }

    @Override
    public Color getColor( String path, Color def )
    {
        Color result = getColor( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isPotionEffect( String path )
    {
        Object object = parent.get( path );
        return object instanceof PotionEffect;
    }

    @Override
    public PotionEffect getPotionEffect( String path )
    {
        return parent.get( path );
    }

    @Override
    public PotionEffect getPotionEffect( String path, PotionEffect def )
    {
        PotionEffect result = getPotionEffect( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isFireworkEffect( String path )
    {
        Object object = parent.get( path );
        return object instanceof FireworkEffect;
    }

    @Override
    public FireworkEffect getFireworkEffect( String path )
    {
        return parent.get( path );
    }

    @Override
    public FireworkEffect getFireworkEffect( String path, FireworkEffect def )
    {
        FireworkEffect result = getFireworkEffect( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isPattern( String path )
    {
        Object object = parent.get( path );
        return object instanceof Pattern;
    }

    @Override
    public Pattern getPattern( String path )
    {
        return parent.get( path );
    }

    @Override
    public Pattern getPattern( String path, Pattern def )
    {
        Pattern result = getPattern( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isLocation( String path )
    {
        Object object = parent.get( path );
        return object instanceof Location;
    }

    @Override
    public Location getLocation( String path )
    {
        return parent.get( path );
    }

    @Override
    public Location getLocation( String path, Location def )
    {
        Location result = getLocation( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isAttributeModifier( String path )
    {
        Object object = parent.get( path );
        return object instanceof AttributeModifier;
    }

    @Override
    public AttributeModifier getAttributeModifier( String path )
    {
        return parent.get( path );
    }

    @Override
    public AttributeModifier getAttributeModifier( String path, AttributeModifier def )
    {
        AttributeModifier result = getAttributeModifier( path );

        if ( result == null )
        {
            update( path, def, false );
        }
        return result == null ? def : result;
    }

    private void update( String path, Object value, boolean overwrite )
    {
        if ( parent instanceof YamlSection )
        {
            ( (YamlSection) parent ).update( path, value, overwrite );
        }
        else
        {
            ( (JsonSection) parent ).update( path, value, overwrite );
        }
    }
}
