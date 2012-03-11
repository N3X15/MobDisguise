package me.desmin88.mobdisguise;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.commands.MDCommand;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import me.desmin88.mobdisguise.utils.DisguiseTask;
import me.desmin88.mobdisguise.utils.MobIdEnum;
import me.desmin88.mobdisguise.utils.PacketUtils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class MobDisguise extends JavaPlugin {
    
    //Player disguising -> player disguised as
    public static PacketUtils pu = new PacketUtils();
    private final MDListener listener = new MDListener(this);
    public static Map<String, DisguiseHandler> players = new HashMap<String, DisguiseHandler>();
    public static final String pref = "[MobDisguise] ";
    public static YamlConfiguration cfg;
    public static boolean perm;
    public static PluginDescriptionFile pdf;
    public static HashSet<String> telelist = new HashSet<String>();
    
    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        System.out.println("[" + pdf.getName() + "]" + " by " + pdf.getAuthors().get(0) + " version " + pdf.getVersion() + " disabled.");
        
    }
    
    @Override
    public void onEnable() {
        MobDisguiseAPI.plugin = this;
        pdf = getDescription();
        final File cfgFile = new File(getDataFolder(), "config.yml");
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
        cfg = (YamlConfiguration) getConfig(); // Get config
        
        boolean needsSave = false;
        
        if (cfg.getKeys(false).isEmpty()) { // Config hasn't been made
            System.out.println(pref + "config.yml not found, making with default values");
            cfg.set("RealDrops.enabled", false);
            cfg.set("Permissions.enabled", true);
            cfg.set("MobTarget.enabled", true);
            cfg.set("DisableItemPickup", true);
            for (final MobIdEnum mobtype : MobIdEnum.values()) {
                cfg.set("Blacklist." + mobtype.name().toLowerCase(), true); // Just making
            }
            needsSave = true;
        }
        if ((cfg.get("MobTarget.enabled") == null) || (cfg.get("DisableItemPickup.enabled") == null)) {
            cfg.set("MobTarget.enabled", true);
            cfg.set("DisableItemPickup.enabled", true);
            needsSave = true;
        }
        if (cfg.get("Blacklist.enderman") == null) {
            cfg.set("Blacklist.enderman", true);
            cfg.set("Blacklist.silverfish", true);
            cfg.set("Blacklist.cavespider", true);
            needsSave = true;
        }
        
        if (needsSave) {
            try {
                cfg.save(cfgFile);
            } catch (final IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        perm = cfg.getBoolean("Permissions.enabled", true);
        
        getServer().getPluginManager().registerEvents(listener, this);
        
        getCommand("md").setExecutor(new MDCommand(this));
        
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new DisguiseTask(this), 1200, 1200);
        System.out.println("[" + pdf.getName() + "]" + " by " + pdf.getAuthors().get(0) + " version " + pdf.getVersion() + " enabled.");
        
    }
    
}