package me.desmin88.mobdisguise.utils;

import java.lang.reflect.Field;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet24MobSpawn;
import net.minecraft.server.Packet29DestroyEntity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtils {
    
    public PacketUtils() {
    }
    
    public void killCarcass(final Player p1) {
        //Make packets out of loop!
        final CraftPlayer p22 = (CraftPlayer) p1;
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (!p1.getWorld().equals(p2.getWorld())) {
                continue;
            }
            if (p2.getName().equals(p1.getName())) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            
        }
    }
    
    public void undisguiseToAll(final Player p1) {
        //Make packets out of loop!
        final CraftPlayer p22 = (CraftPlayer) p1;
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
        final Packet20NamedEntitySpawn p20 = new Packet20NamedEntitySpawn(p22.getHandle());
        
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (!p1.getWorld().equals(p2.getWorld())) {
                continue;
            }
            if (p2 == p1) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p20);
        }
    }
    
    public void disguiseToAll(final Player p1) {
        //Make packets out of loop!
        final DisguiseHandler dh = MobDisguiseAPI.getPlayerDisguise(p1);
        if (dh == null)
            return;
        final Packet24MobSpawn p24 = dh.createSpawnMobPacket();
        for (final Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (!p1.getWorld().equals(p2.getWorld())) {
                continue;
            }
            if (p2 == p1) {
                continue;
            }
            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(p24);
        }
    }
    
    //Begin code for p2p disguising
    public void disguisep2pToAll(final Player p, final String name) {
        final Packet20NamedEntitySpawn p20 = packetMaker(p, name);
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
        p.setDisplayName(name);
        
        for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (!p.getWorld().equals(p1.getWorld())) {
                continue;
            }
            if (p1 == p) {
                continue;
            }
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
        }
    }
    
    public void undisguisep2pToAll(final Player p) {
        p.setDisplayName(p.getName());
        final Packet20NamedEntitySpawn p20 = packetMaker(p, p.getName());
        final Packet29DestroyEntity p29 = new Packet29DestroyEntity(p.getEntityId()); //Must destroy, don't want doubles lololololol
        for (final Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1 == p) {
                continue;
            }
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p29);
            ((CraftPlayer) p1).getHandle().netServerHandler.sendPacket(p20);
        }
    }
    
    public Packet20NamedEntitySpawn packetMaker(final Player p, final String name) {
        final Location loc = p.getLocation();
        final Packet20NamedEntitySpawn packet = new Packet20NamedEntitySpawn();
        packet.a = p.getEntityId();
        packet.b = name; //Set the name of the player to the name they want.
        packet.c = (int) loc.getX();
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) (((int) loc.getYaw() * 256.0F) / 360.0F);
        packet.g = (byte) ((int) ((loc.getPitch() * 256.0F) / 360.0F));
        packet.h = p.getItemInHand().getTypeId();
        return packet;
        
    }
    
    public Packet24MobSpawn packetMaker(final Player p1, final Byte id) {
        DisguiseHandler dh = null;
        if (MobDisguise.players.containsKey(p1.getName())) {
            dh = MobDisguise.players.get(p1.getName());
        } else
            return null;
        
        final Location loc = p1.getLocation();
        final Packet24MobSpawn packet = new Packet24MobSpawn();
        packet.a = ((CraftPlayer) p1).getEntityId();
        packet.b = id.byteValue();
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) (((int) loc.getYaw() * 256.0F) / 360.0F);
        packet.g = (byte) ((int) ((loc.getPitch() * 256.0F) / 360.0F));
        Field datawatcher;
        try {
            datawatcher = packet.getClass().getDeclaredField("h");
            datawatcher.setAccessible(true);
            datawatcher.set(packet, dh.getDataWatcher());
            datawatcher.setAccessible(false);
        } catch (final Exception e) {
            System.out.println(MobDisguise.pref + "Error making packet?!");
            return null;
        }
        return packet;
    }
    
}
