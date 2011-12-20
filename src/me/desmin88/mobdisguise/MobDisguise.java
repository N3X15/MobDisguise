package me.desmin88.mobdisguise;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.commands.MDCommand;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import me.desmin88.mobdisguise.listeners.MDEntityListener;
import me.desmin88.mobdisguise.listeners.MDPlayerListener;
import me.desmin88.mobdisguise.utils.DisguiseTask;
import me.desmin88.mobdisguise.utils.MobIdEnum;
import me.desmin88.mobdisguise.utils.PacketUtils;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class MobDisguise extends JavaPlugin {
    
    //Player disguising -> player disguised as
    public static PacketUtils pu = new PacketUtils();
    public final MDPlayerListener playerlistener = new MDPlayerListener(this);
    public final MDEntityListener entitylistener = new MDEntityListener(this);
    public static Map<String, DisguiseHandler> players = new HashMap<String, DisguiseHandler>();
    public static final String pref = "[MobDisguise] ";
    public static Configuration cfg;
    public static boolean perm;
    public static PluginDescriptionFile pdf;
    public static HashSet<String> telelist = new HashSet<String>();
    
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        System.out.println("[" + pdf.getName() + "]" + " by " + pdf.getAuthors().get(0) + " version " + pdf.getVersion() + " disabled.");
        
    }
    
    public void onEnable() {
        MobDisguiseAPI.plugin = this;
        pdf = getDescription();
        // Begin config code
        if (!new File(getDataFolder(), "config.yml").exists()) {
            try {
                getDataFolder().mkdir();
                new File(getDataFolder(), "config.yml").createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
                System.out.println(pref + "Error making config.yml?!");
                getServer().getPluginManager().disablePlugin(this); //Cleanup
                return;
            }
        }
        cfg = getConfiguration(); // Get config
        
        if (cfg.getKeys().isEmpty()) { // Config hasn't been made
            System.out.println(pref + "config.yml not found, making with default values");
            cfg.setProperty("RealDrops.enabled", false);
            cfg.setProperty("Permissions.enabled", true);
            cfg.setProperty("MobTarget.enabled", true);
            cfg.setProperty("DisableItemPickup", true);
            for (final MobIdEnum mobtype : MobIdEnum.values()) {
                cfg.setHeader("#Setting a mobtype to false will not allow a player to disguise as that type");
                cfg.setProperty("Blacklist." + mobtype.name().toLowerCase(), true); // Just making
            }
            cfg.save();
        }
        if ((cfg.getProperty("MobTarget.enabled") == null) || (cfg.getProperty("DisableItemPickup.enabled") == null)) {
            cfg.setProperty("MobTarget.enabled", true);
            cfg.setProperty("DisableItemPickup.enabled", true);
            cfg.save();
        }
        if (cfg.getProperty("Blacklist.enderman") == null) {
            cfg.setProperty("Blacklist.enderman", true);
            cfg.setProperty("Blacklist.silverfish", true);
            cfg.setProperty("Blacklist.cavespider", true);
        }
        
        cfg.save();
        perm = cfg.getBoolean("Permissions.enabled", true);
        
        final PluginManager pm = getServer().getPluginManager();
        getCommand("md").setExecutor(new MDCommand(this));
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerlistener, Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerlistener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, playerlistener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entitylistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, entitylistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, playerlistener, Priority.Normal, this);
        
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new DisguiseTask(this), 1200, 1200);
        System.out.println("[" + pdf.getName() + "]" + " by " + pdf.getAuthors().get(0) + " version " + pdf.getVersion() + " enabled.");
        
    }
    
}