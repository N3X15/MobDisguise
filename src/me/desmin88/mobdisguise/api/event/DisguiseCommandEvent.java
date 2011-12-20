package me.desmin88.mobdisguise.api.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when the MobDisguise command is used.
 * 
 * @author iffa
 * 
 */
public class DisguiseCommandEvent extends Event implements Cancellable {
    private static final long serialVersionUID = -1970653423890974618L;
    private final CommandSender sender;
    private final String[] args;
    private boolean canceled;
    
    public DisguiseCommandEvent(final String event, final CommandSender sender, final String[] args) {
        super(event);
        this.sender = sender;
        this.args = args;
    }
    
    /**
     * Gets the commandsender.
     * 
     * @return CommandSender
     */
    public CommandSender getSender() {
        return sender;
    }
    
    /**
     * Gets the command arguments.
     * 
     * @return Args
     */
    public String[] getArgs() {
        return args;
    }
    
    public boolean isCancelled() {
        return canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        canceled = cancel;
    }
    
}
