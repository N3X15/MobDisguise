package me.desmin88.mobdisguise.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * DisguiseEvent
 * 
 * @author iffa
 * 
 */
public class DisguiseEvent extends Event implements Cancellable {
    private static final long serialVersionUID = -6426402822588097606L;
    private final Player player;
    private boolean canceled;
    
    public DisguiseEvent(final String event, final Player player) {
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
        // TODO Auto-generated method stub
        return canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        canceled = cancel;
    }
    
}
