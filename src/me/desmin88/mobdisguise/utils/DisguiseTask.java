package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DisguiseTask implements Runnable {
    public MobDisguise plugin;
    
    public DisguiseTask(final MobDisguise instance) {
        plugin = instance;
    }
    
    public void run() {
        for (final String playerName : MobDisguise.players.keySet()) {
            final Player p = Bukkit.getServer().getPlayer(playerName);
            if (p == null) {
                continue;
            }
            MobDisguiseAPI.getPlayerDisguise(p).sendUpdate();
            
        }
    }
}
