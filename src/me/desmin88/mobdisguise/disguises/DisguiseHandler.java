package me.desmin88.mobdisguise.disguises;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.Packet40EntityMetadata;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DisguiseHandler {
    protected MobDisguise plugin;
    protected Player player;
    protected DataWatcher datawatcher;
    
    public DisguiseHandler(Player pl, MobDisguise p) {
        plugin = p;
        player = pl;
        datawatcher = new DataWatcher();
        this.datawatcher.a(0, Byte.valueOf((byte) 0));
    }
    
    public boolean handleEffectCommand(String cmd, String[] arg) {
        player.sendMessage(MobDisguise.pref + "Sorry, no effects available for this mob.");
        return false;
    }
    
    public DataWatcher getDataWatcher() {
        // TODO Auto-generated method stub
        return datawatcher;
    }
    
    public void sendUpdate() {
        // Send DataWatcher updates
        DataWatcher dw = getDataWatcher();
        if (dw.a()) {
            Packet40EntityMetadata p40 = new Packet40EntityMetadata(player.getEntityId(), dw);
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(p40);
            }
        }
    }
}
