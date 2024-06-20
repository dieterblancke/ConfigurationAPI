package dev.endoy.configuration.api;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public interface ISpigotSection
{

    /**
     * Used to check if the value bound to the given path is a Vector.
     *
     * @param path The path you want to check.
     * @return True if the value is a Vector, false if not.
     */
    boolean isVector( String path );

    /**
     * Gets the requested Vector by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    Vector getVector( String path );

    /**
     * Gets the requested Vector by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    Vector getVector( String path, Vector def );

    /**
     * Used to check if the value bound to the given path is an OfflinePlayer.
     *
     * @param path The path you want to check.
     * @return True if the value is an OfflinePlayer, false if not.
     */
    boolean isOfflinePlayer( String path );

    /**
     * Gets the requested OfflinePlayer by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    OfflinePlayer getOfflinePlayer( String path );

    /**
     * Gets the requested OfflinePlayer by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    OfflinePlayer getOfflinePlayer( String path, OfflinePlayer def );

    /**
     * Used to check if the value bound to the given path is an ItemStack.
     *
     * @param path The path you want to check.
     * @return True if the value is an ItemStack, false if not.
     */
    boolean isItemStack( String path );

    /**
     * Gets the requested ItemStack by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    ItemStack getItemStack( String path );

    /**
     * Gets the requested ItemStack by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    ItemStack getItemStack( String path, ItemStack def );

    /**
     * Used to check if the value bound to the given path is a Color.
     *
     * @param path The path you want to check.
     * @return True if the value is a Color, false if not.
     */
    boolean isColor( String path );

    /**
     * Gets the requested Color by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    Color getColor( String path );

    /**
     * Gets the requested Color by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    Color getColor( String path, Color def );

    /**
     * Used to check if the value bound to the given path is a PotionEffect.
     *
     * @param path The path you want to check.
     * @return True if the value is a PotionEffect, false if not.
     */
    boolean isPotionEffect( String path );

    /**
     * Gets the requested PotionEffect by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    PotionEffect getPotionEffect( String path );

    /**
     * Gets the requested PotionEffect by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    PotionEffect getPotionEffect( String path, PotionEffect def );

    /**
     * Used to check if the value bound to the given path is a FireworkEffect.
     *
     * @param path The path you want to check.
     * @return True if the value is a FireworkEffect, false if not.
     */
    boolean isFireworkEffect( String path );

    /**
     * Gets the requested FireworkEffect by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    FireworkEffect getFireworkEffect( String path );

    /**
     * Gets the requested FireworkEffect by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    FireworkEffect getFireworkEffect( String path, FireworkEffect def );

    /**
     * Used to check if the value bound to the given path is a Pattern.
     *
     * @param path The path you want to check.
     * @return True if the value is a Pattern, false if not.
     */
    boolean isPattern( String path );

    /**
     * Gets the requested Pattern by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    Pattern getPattern( String path );

    /**
     * Gets the requested Pattern by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    Pattern getPattern( String path, Pattern def );

    /**
     * Used to check if the value bound to the given path is a Location.
     *
     * @param path The path you want to check.
     * @return True if the value is a Location, false if not.
     */
    boolean isLocation( String path );

    /**
     * Gets the requested Location by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    Location getLocation( String path );

    /**
     * Gets the requested Location by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    Location getLocation( String path, Location def );

    /**
     * Used to check if the value bound to the given path is a AttributeModifier.
     *
     * @param path The path you want to check.
     * @return True if the value is a AttributeModifier, false if not.
     */
    boolean isAttributeModifier( String path );

    /**
     * Gets the requested AttributeModifier by path.
     *
     * @param path The path of which you need the value.
     * @return The value bound to this path.
     */
    AttributeModifier getAttributeModifier( String path );

    /**
     * Gets the requested AttributeModifier by path, sets default if not present.
     *
     * @param path The path of which you need the value.
     * @param def  The default value this path should get if not present.
     * @return The value bound to this path, default if not present.
     */
    AttributeModifier getAttributeModifier( String path, AttributeModifier def );

}
