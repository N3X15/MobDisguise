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
import org.bukkit.inventory.ItemStack;

public class DisguiseHandler {
    public int entID = 0;
    protected MobDisguise plugin;
    protected Player player;
    protected DataWatcher datawatcher;
    private final byte mobId;
    
    public DisguiseHandler(final Player pl, final MobDisguise p, final byte mobID) {
        plugin = p;
        player = pl;
        datawatcher = new DataWatcher();
        mobId = mobID;
        refresh();
    }
    
    public int getAge() {
        return datawatcher.getInt(12);
    }
    
    public void setAge(final int i) {
        datawatcher.watch(12, Integer.valueOf(i));
    }
    
    public boolean onFire() {
        return getFlag(0);
    }
    
    public void setFire(final boolean burnan) {
        setFlag(0, burnan);
    }
    
    public boolean isSneaking() {
        return getFlag(1);
    }
    
    public void setSneak(final boolean flag) {
        setFlag(1, flag);
    }
    
    public boolean isSprinting() {
        return getFlag(3);
    }
    
    public void setSprinting(final boolean flag) {
        setFlag(3, flag);
    }
    
    public void setHungry(final boolean flag) {
        setFlag(4, flag);
    }
    
    protected boolean getFlag(final int bit) {
        return (datawatcher.getByte(0) & (1 << bit)) != 0;
    }
    
    protected void setFlag(final int bit, final boolean status) {
        final byte b0 = datawatcher.getByte(0);
        
        if (status) {
            datawatcher.watch(0, Byte.valueOf((byte) (b0 | (1 << bit))));
        } else {
            datawatcher.watch(0, Byte.valueOf((byte) (b0 & ~(1 << bit))));
        }
    }
    
    public int getAirTicks() {
        return datawatcher.b(1);
    }
    
    public void setAirTicks(final int i) {
        datawatcher.watch(1, Short.valueOf((short) i));
    }
    
    public boolean handleEffectCommand(final String cmd, final String[] arg) {
        player.sendMessage(MobDisguise.pref + "Sorry, no effects available for this mob.");
        return false;
    }
    
    public DataWatcher getDataWatcher() {
        // TODO Auto-generated method stub
        return datawatcher;
    }
    
    public void sendUpdate() {
        // Send DataWatcher updates
        final DataWatcher dw = getDataWatcher();
        final Packet24MobSpawn p24 = createSpawnMobPacket();
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.getWorld().equals(p2.getWorld())) {
                continue;
            }
            if (p2 == player) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p24);
        }
        if (dw.a()) {
            final Packet40EntityMetadata p40 = new Packet40EntityMetadata(player.getEntityId(), dw);
            for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
                ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(p40);
            }
        }
    }
    
    public Packet24MobSpawn createSpawnMobPacket() {
        final Location loc = player.getLocation();
        final Packet24MobSpawn packet = new Packet24MobSpawn();
        packet.a = ((CraftPlayer) player).getEntityId();
        packet.b = mobId;
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) (((int) loc.getYaw() * 256.0F) / 360.0F);
        packet.g = (byte) ((int) ((loc.getPitch() * 256.0F) / 360.0F));
        Field datawatcher;
        try {
            datawatcher = packet.getClass().getDeclaredField("h");
            datawatcher.setAccessible(true);
            datawatcher.set(packet, getDataWatcher());
            datawatcher.setAccessible(false);
        } catch (final Exception e) {
            System.out.println(MobDisguise.pref + "Error making packet?!");
            return null;
        }
        return packet;
    }
    
    public Integer getMobID() {
        // TODO Auto-generated method stub
        return Integer.valueOf(mobId);
    }
    
    public void removeDisguise() {
        MobDisguise.pu.undisguiseToAll(player);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public ItemStack getDrops() {
        // TODO Auto-generated method stub
        return new ItemStack(0);
    }
    
    public void refresh() {
        datawatcher = new DataWatcher();
        datawatcher.a(0, Byte.valueOf((byte) 0));
        datawatcher.a(1, Short.valueOf((short) 300));
        datawatcher.a(12, new Integer(0));
    }
}
