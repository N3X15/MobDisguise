package me.desmin88.mobdisguise.api;

import me.desmin88.mobdisguise.api.event.DisguiseAsMobEvent;
import me.desmin88.mobdisguise.api.event.DisguiseAsPlayerEvent;
import me.desmin88.mobdisguise.api.event.DisguiseCommandEvent;
import me.desmin88.mobdisguise.api.event.UnDisguiseEvent;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * MobDisguiseListener for events of MobDisguise.
 * 
 * @author iffa
 * 
 */
public class MobDisguiseListener implements Listener {
    /**
     * Called when a player disguises as a mob.
     * 
     * @param event Event data
     */
    public void onDisguiseAsMob(final DisguiseAsMobEvent event) {
    }
    
    /**
     * Called when a player disguises as another player.
     * 
     * @param event Event data
     */
    public void onDisguiseAsPlayer(final DisguiseAsPlayerEvent event) {
    }
    
    /**
     * Called when a player undisguises.
     * 
     * @param event Event data
     */
    public void onUnDisguise(final UnDisguiseEvent event) {
    }
    
    /**
     * Called when the MobDisguise command is used.
     * 
     * @param event Event data
     */
    public void onMobDisguiseCommand(final DisguiseCommandEvent event) {
    }
    
    /**
     * Handles the events.
     * 
     * @param event Event data
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCustomEvent(final Event event) {
        if (event instanceof DisguiseAsMobEvent) {
            onDisguiseAsMob((DisguiseAsMobEvent) event);
        } else if (event instanceof DisguiseAsPlayerEvent) {
            onDisguiseAsPlayer((DisguiseAsPlayerEvent) event);
        } else if (event instanceof UnDisguiseEvent) {
            onUnDisguise((UnDisguiseEvent) event);
        } else if (event instanceof DisguiseCommandEvent) {
            onMobDisguiseCommand((DisguiseCommandEvent) event);
        }
    }
}
