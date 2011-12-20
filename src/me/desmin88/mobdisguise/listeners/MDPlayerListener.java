package me.desmin88.mobdisguise.listeners;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.utils.DisguiseTask;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MDPlayerListener extends PlayerListener {
    
    private final MobDisguise plugin;
    
    public MDPlayerListener(final MobDisguise instance) {
        plugin = instance;
    }
    
    @Override
    public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer()) && MobDisguise.cfg.getBoolean("DisableItemPickup.enabled", true)) {
            if (MobDisguiseAPI.getPlayerDisguise(event.getPlayer()).getMobID() != MobIdEnum.PLAYER.id) {
                event.setCancelled(true);
            }
        }
    }
    
    @Override
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            MobDisguiseAPI.getPlayerDisguise(event.getPlayer()).entID = -1;
            //Should fix the "carcass" mob when disguised
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    MobDisguise.pu.killCarcass(event.getPlayer());
                }
            }, 5);
            
        }
    }
    
    //Waiting for my stinking pull.
    @Override
    public void onPlayerAnimation(final PlayerAnimationEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
    
    @Override
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
    
    @Override
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        if (MobDisguise.telelist.contains(event.getPlayer().getName())) {
            MobDisguise.telelist.remove(event.getPlayer().getName());
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin), 8);
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.getPlayer().sendMessage(MobDisguise.pref + "You have been disguised because you teleported");
        }
    }
    
    @Override
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin), 8);
        
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.getPlayer().sendMessage(MobDisguise.pref + "You have been disguised because you died");
        }
    }
    
    @Override
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin), 20);
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            MobDisguise.telelist.add(event.getPlayer().getName());
            MobDisguiseAPI.getPlayerDisguise(event.getPlayer()).entID = event.getPlayer().getEntityId();
            
            event.getPlayer().sendMessage(MobDisguise.pref + "You have been disguised because you relogged");
            
        }
        
    }
}
