package me.desmin88.mobdisguise.listeners;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

public class MDEntityListener extends EntityListener {
    @SuppressWarnings("unused")
    private final MobDisguise plugin;
    
    public MDEntityListener(final MobDisguise instance) {
        plugin = instance;
    }
    
    @Override
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
    
    @Override
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
}