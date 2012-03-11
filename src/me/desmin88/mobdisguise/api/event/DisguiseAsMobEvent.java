package me.desmin88.mobdisguise.api.event;

import org.bukkit.entity.Player;

/**
 * Event data for when a player disguises as a mob.
 * @author iffa
 * 
 */
public class DisguiseAsMobEvent extends DisguiseEvent {
    private final String mobtype;
    
    public DisguiseAsMobEvent(final String event, final Player player, final String mobtype) {
        super(event, player);
        this.mobtype = mobtype;
    }
    
    /**
     * Gets the mobtype the player is disguising as.
     * 
     * @return Mobtype
     */
    public String getMobType() {
        return mobtype;
    }
    
}
