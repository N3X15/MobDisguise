package me.desmin88.mobdisguise.utils;

import me.desmin88.mobdisguise.MobDisguise;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.Packet40EntityMetadata;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DisguiseTask implements Runnable {
    public MobDisguise plugin;

    public DisguiseTask(MobDisguise instance) {
        plugin = instance;
    }

    public void run() {
        for (String s : MobDisguise.disList) {
            if(Bukkit.getServer().getPlayer(s) == null) {
                continue;
            }
            Player temp = plugin.getServer().getPlayer(s);
            int mid = MobDisguise.playerMobId.get(temp.getName());
            if(!MobDisguise.disguiseHandlers.containsKey(temp.getName())) {
                MobDisguise.disguiseHandlers.put(temp.getName(), MobIdEnum.getFromByte(mid).instantiate(temp,plugin));
            }
            
            if(MobDisguise.playerdislist.contains(temp.getName())) {
                MobDisguise.pu.disguisep2pToAll(temp, MobDisguise.p2p.get(temp.getName()));
            }
            else {
                MobDisguise.pu.disguiseToAll(temp);
            }
        }
    }
}
