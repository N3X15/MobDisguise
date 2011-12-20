package me.desmin88.mobdisguise.commands;

import java.util.Arrays;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import me.desmin88.mobdisguise.api.event.DisguiseCommandEvent;
import me.desmin88.mobdisguise.api.event.UnDisguiseEvent;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author desmin88
 * @author iffa
 * 
 */
public class MDCommand implements CommandExecutor {
    public MDCommand(final MobDisguise instance) {
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
        /* Listener notify start */
        final DisguiseCommandEvent ev = new DisguiseCommandEvent("DisguiseCommandEvent", sender, args);
        Bukkit.getServer().getPluginManager().callEvent(ev);
        if (ev.isCancelled())
            return true;
        /* Listener notify end */
        if (sender instanceof Player) {
            final Player s = (Player) sender;
            if (args.length == 0) { // Undisguising, player types /md
                if (!MobDisguise.players.containsKey(s.getName())) {
                    s.sendMessage(MobDisguise.pref + "You are not disguised, so you can't undisguise!");
                    return true;
                } else {
                    /* Listener notify start */
                    final UnDisguiseEvent e = new UnDisguiseEvent("UnDisguiseEvent", s, false);
                    Bukkit.getServer().getPluginManager().callEvent(e);
                    if (e.isCancelled())
                        return true;
                    /* Listener notify end */
                    MobDisguiseAPI.undisguisePlayer(s);
                    s.sendMessage(MobDisguise.pref + "You have undisguised as a different player and returned back to normal!");
                    return true;
                }
            }
            
            if (args[0].equalsIgnoreCase("e")) {
                if (!MobDisguiseAPI.isDisguised(s)) {
                    s.sendMessage(MobDisguise.pref + "You aren't disguised.");
                    return true;
                }
                final DisguiseHandler dh = MobDisguise.players.get(s.getName());
                
                String[] arg = new String[0];
                if (args.length > 2) {
                    arg = Arrays.copyOfRange(args, 2, args.length - 1);
                }
                if (dh == null)
                    return false;
                return dh.handleEffectCommand(args[1], arg);
            }
            
            if (args[0].equalsIgnoreCase("types")) { // They want to know valid types of mobs
                String available = new String("");
                
                for (final MobIdEnum mob : MobIdEnum.values()) {
                    
                    if (s.hasPermission("mobdisguise." + mob.name().toLowerCase()) || s.isOp()) {
                        available = available + mob.name().toLowerCase() + ", ";
                    }
                }
                s.sendMessage(MobDisguise.pref + available);
                return true;
            }
            if (args[0].equalsIgnoreCase("stats")) { // They want to know they're current disguing status
            
                if (!MobDisguiseAPI.isDisguised(s)) {
                    s.sendMessage(MobDisguise.pref + "You are currently NOT disguised!");
                    return true;
                } else {
                    final DisguiseHandler dh = MobDisguiseAPI.getPlayerDisguise(s);
                    s.sendMessage(MobDisguise.pref + "You are currently disguised as " + dh.toString());
                    return true;
                }
                
            }
            
            if (args[0].equalsIgnoreCase("p") && (args.length == 2)) {
                if (MobDisguise.perm && !s.isOp()) {
                    if (!s.hasPermission("mobdisguise.player")) {
                        s.sendMessage(MobDisguise.pref + "You don't have permission to change into other players!");
                        return true;
                    }
                }
                if (!MobDisguise.perm && !s.isOp()) {
                    s.sendMessage(MobDisguise.pref + "You are not an OP and perms are disabled!");
                    return true;
                }
                if (args[1].length() > 16) {
                    s.sendMessage(MobDisguise.pref + "That username is too long!");
                    return true;
                }
                MobDisguiseAPI.disguisePlayerAsPlayer(s, args[1]);
                s.sendMessage(MobDisguise.pref + "You have disguised as player " + args[1]);
                return true;
            }
            
            if (args.length == 1) { // Means they're trying to disguise
                final String mobtype = args[0].toLowerCase();
                if (MobIdEnum.get(mobtype) == null) {
                    s.sendMessage(MobDisguise.pref + "Invalid mob type!");
                    return true;
                }
                if (MobDisguise.perm && !s.isOp()) {
                    if (!s.hasPermission("mobdisguise." + mobtype)) {
                        s.sendMessage(MobDisguise.pref + "You don't have permission for this mob!");
                        return true;
                    }
                }
                if (!MobDisguise.perm && !s.isOp()) {
                    s.sendMessage(MobDisguise.pref + "You are not an OP and perms are disabled!");
                    return true;
                }
                if (!MobDisguise.cfg.getBoolean("BlackList." + mobtype, true)) {
                    s.sendMessage(MobDisguise.pref + "This mob type has been restricted!");
                    return true;
                }
                MobDisguiseAPI.disguisePlayer(s, mobtype);
                s.sendMessage(MobDisguise.pref + "You have been disguised as a " + args[0].toLowerCase() + "!");
                return true;
                
            }
            
        }
        return false;
    }
}
