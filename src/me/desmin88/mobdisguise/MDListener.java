package me.desmin88.mobdisguise;

import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.utils.DisguiseTask;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MDListener implements Listener {
    private final MobDisguise plugin;
    
    public MDListener(final MobDisguise instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityTarget(final EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            final Player p = (Player) event.getTarget();
            if (MobDisguiseAPI.isDisguised(p) && MobDisguise.cfg.getBoolean("MobTarget.enabled", true)) {
                if (MobDisguiseAPI.getPlayerDisguise(p).getMobID() != MobIdEnum.PLAYER.id) {
                    event.setCancelled(true);
                }
            }
            
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player p = (Player) event.getEntity();
            if (MobDisguise.cfg.getBoolean("RealDrops.enabled", false) && MobDisguiseAPI.isDisguised(p)) {
                event.getDrops().clear();
                if (MobDisguiseAPI.getPlayerDisguise(p).getMobID() != MobIdEnum.PLAYER.id) {
                    p.getWorld().dropItemNaturally(p.getLocation(), MobDisguiseAPI.getPlayerDisguise(p).getDrops());
                }
                
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer()) && MobDisguise.cfg.getBoolean("DisableItemPickup.enabled", true)) {
            if (MobDisguiseAPI.getPlayerDisguise(event.getPlayer()).getMobID() != MobIdEnum.PLAYER.id) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
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
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerAnimation(final PlayerAnimationEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnter(final PlayerBedEnterEvent event) {
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
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
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin), 8);
        
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            event.getPlayer().sendMessage(MobDisguise.pref + "You have been disguised because you died");
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new DisguiseTask(plugin), 20);
        if (MobDisguiseAPI.isDisguised(event.getPlayer())) {
            MobDisguise.telelist.add(event.getPlayer().getName());
            MobDisguiseAPI.getPlayerDisguise(event.getPlayer()).entID = event.getPlayer().getEntityId();
            
            event.getPlayer().sendMessage(MobDisguise.pref + "You have been disguised because you relogged");
            
        }
        
    }
}