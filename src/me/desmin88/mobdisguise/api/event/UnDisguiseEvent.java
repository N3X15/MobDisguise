package me.desmin88.mobdisguise.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event data for when a player undisguises.
 * 
 * @author iffa
 * 
 */
public class UnDisguiseEvent extends Event implements Cancellable {
    private final Player player;
    private boolean canceled;
    private final HandlerList handlers = new HandlerList();
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public UnDisguiseEvent(final String event, final Player player, final boolean mob) {
        this.player = player;
    }
    
    /**
     * Gets the player associated with this event.
     * 
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
    
    public boolean isCancelled() {
        return canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        canceled = cancel;
    }
    
}
