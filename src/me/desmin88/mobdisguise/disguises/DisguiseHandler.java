package me.desmin88.mobdisguise.disguises;

import java.lang.reflect.Field;

import me.desmin88.mobdisguise.MobDisguise;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet40EntityMetadata;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DisguiseHandler {
    protected MobDisguise plugin;
    protected Player player;
    protected DataWatcher datawatcher;
    private byte mobId;
    
    public DisguiseHandler(Player pl, MobDisguise p, byte mobID) {
        plugin = p;
        player = pl;
        datawatcher = new DataWatcher();
        this.datawatcher.a(0, Byte.valueOf((byte) 0));
        mobId=mobID;
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

    public Packet24MobSpawn createSpawnMobPacket() {
        Location loc = player.getLocation();
        Packet24MobSpawn packet = new Packet24MobSpawn();
        packet.a = ((CraftPlayer) player).getEntityId();
        packet.b = mobId;
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) ((int) loc.getYaw() * 256.0F / 360.0F);
        packet.g = (byte) ((int) (loc.getPitch() * 256.0F / 360.0F));
        Field datawatcher;
        try {
            datawatcher = packet.getClass().getDeclaredField("h");
            datawatcher.setAccessible(true);
            datawatcher.set(packet, getDataWatcher());
            datawatcher.setAccessible(false);
        } catch (Exception e) {
            System.out.println(MobDisguise.pref + "Error making packet?!");
            return null;
        } 
        return packet;
    }

    public Integer getMobID() {
        // TODO Auto-generated method stub
        return Integer.valueOf(mobId);
    }
}
