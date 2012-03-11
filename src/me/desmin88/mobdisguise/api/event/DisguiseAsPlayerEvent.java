package me.desmin88.mobdisguise.api.event;

import org.bukkit.entity.Player;

/**
 * Event data for when a player disguises as another player.
 * 
 * @author iffa
 * 
 */
public class DisguiseAsPlayerEvent extends DisguiseEvent {
    private final String name;
    
    public DisguiseAsPlayerEvent(final String event, final Player player, final String name) {
        super(event, player);
        this.name = name;
    }
    
    /**
     * Gets the player name the player is disguising as.
     * 
     * @return Player name
     */
    public String getName() {
        return name;
    }
    
}
