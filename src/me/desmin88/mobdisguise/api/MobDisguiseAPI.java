package me.desmin88.mobdisguise.api;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.event.DisguiseAsMobEvent;
import me.desmin88.mobdisguise.api.event.DisguiseAsPlayerEvent;
import me.desmin88.mobdisguise.api.event.UnDisguiseEvent;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import me.desmin88.mobdisguise.disguises.PlayerHandler;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Basic API to disguise players.
 * 
 * @author desmin88
 * @author iffa
 * 
 */
public class MobDisguiseAPI {
    public static MobDisguise plugin;
    
    /**
     * Disguises a player as a player.
     * 
     * @param p Player to disguise
     * @param name Player name to disguise as
     * 
     * @return true if succesful
     */
    public static boolean disguisePlayerAsPlayer(final Player p, String name) {
        if (isDisguised(p))
            return false;
        if (name.length() > 16) {
            System.out.println(MobDisguise.pref + "Error, some other plugin is setting a name over 16 characters, truncating.");
            final String tmp = name.substring(0, 16);
            name = tmp;
        }
        /* Listener notify start */
        final DisguiseAsPlayerEvent e = new DisguiseAsPlayerEvent("DisguiseAsPlayerEvent", p, name);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (e.isCancelled())
            return false;
        /* Listener notify end */
        final PlayerHandler dp = new PlayerHandler(p, MobDisguiseAPI.plugin, name);
        setupDisguise(dp);
        return true;
    }
    
    /**
     * Undisguises a player who is disguised as another player.
     * @param p Player to undisguise
     * @param name ???
     * @return true if successful
     */
    public static boolean undisguisePlayerAsPlayer(final Player p, final String name) {
        if (!isDisguised(p))
            return false;
        /* Listener notify start */
        final UnDisguiseEvent e = new UnDisguiseEvent("UnDisguiseEvent", p, false);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (e.isCancelled())
            return false;
        /* Listener notify end */
        teardownDisguise(p);
        return true;
    }
    
    /**
     * Disguises a player as a mob.
     * 
     * @param p Player to disguise
     * @param mobtype Mob to disguise as
     * 
     * @return true if successful
     */
    public static boolean disguisePlayer(final Player p, final String mobtype) {
        if (MobIdEnum.get(mobtype) == null)
            return false;
        if (isDisguised(p))
            return false;
        /* Listener notify start */
        final DisguiseAsMobEvent e = new DisguiseAsMobEvent("DisguiseAsMobEvent", p, mobtype);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (e.isCancelled())
            return false;
        /* Listener notify end */
        setupDisguise(MobIdEnum.get(mobtype).instantiate(p, plugin));
        return true;
    }
    
    private static void setupDisguise(final DisguiseHandler dh) {
        MobDisguise.players.put(dh.getPlayer().getName(), dh);
    }
    
    /**
     * Undisguises a player who is disguised as a mob.
     * 
     * @param p Player to undisguise
     * 
     * @return true if successful
     */
    public static boolean undisguisePlayer(final Player p) {
        if (!isDisguised(p))
            return false;
        /* Listener notify start */
        final UnDisguiseEvent e = new UnDisguiseEvent("UnDisguiseEvent", p, true);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if (e.isCancelled())
            return false;
        /* Listener notify end */
        teardownDisguise(p);
        return true;
        
    }
    
    private static void teardownDisguise(final Player p) {
        final DisguiseHandler dh = getPlayerDisguise(p);
        dh.removeDisguise();
        MobDisguise.players.remove(p.getName());
    }
    
    /**
     * Checks if a player is disguised.
     * 
     * @param p Player
     * 
     * @return true if disguised
     */
    public static boolean isDisguised(final Player p) {
        return MobDisguise.players.containsKey(p.getName());
    }
    
    public static DisguiseHandler getPlayerDisguise(final Player player) {
        if (MobDisguise.players.containsKey(player.getName()))
            return MobDisguise.players.get(player.getName());
        return null;
    }
    
}
