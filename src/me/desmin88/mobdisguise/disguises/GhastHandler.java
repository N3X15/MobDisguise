package me.desmin88.mobdisguise.disguises;

import java.util.logging.Logger;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GhastHandler extends DisguiseHandler {
    
    public GhastHandler(final Player pl, final MobDisguise p) {
        super(pl, p, MobIdEnum.GHAST.id);
        Logger.getLogger("Minecraft").info(pl.getName() + " has been disguised as a ghast.");
    }
    
    @Override
    public boolean handleEffectCommand(final String cmd, final String[] arg) {
        if (cmd.equalsIgnoreCase("fireball")) {
            if (datawatcher == null) {
                player.sendMessage("Sorry, it appears that the datawatcher we need is null.");
                return true;
            }
            greatBallsOfFire();
            return true;
        }
        player.sendMessage(MobDisguise.pref + "/md e fireball");
        return false;
    }
    
    private void greatBallsOfFire() {
        final Vector direction = player.getEyeLocation().getDirection().multiply(2);
        player.getWorld().spawn(getPlayer().getEyeLocation().add(direction.getX(), direction.getY(), direction.getZ()), Fireball.class);
    }
}
