package me.desmin88.mobdisguise.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player undisguises.
 * 
 * @author iffa
 * 
 */
public class UnDisguiseEvent extends Event implements Cancellable {
    private static final long serialVersionUID = -5100103933008602505L;
    private final Player player;
    private boolean canceled;
    
    public UnDisguiseEvent(final String event, final Player player, final boolean mob) {
        super(event);
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
