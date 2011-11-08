package me.desmin88.mobdisguise.disguises;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;

public class CreeperHandler extends DisguiseHandler {
    Timer explodeTimer = new Timer();
    public CreeperHandler(Player pl, MobDisguise p) {
        super(pl, p);
    }

    class ExplodeTask extends TimerTask {
        public void run() {
            setExploding(false);
            explode();
        }
    }
    
    @Override
    public boolean handleEffectCommand(String cmd, String[] arg) {
        if (cmd.equalsIgnoreCase("explode")) {
            setExploding(true);
            explodeTimer.schedule(new ExplodeTask(), 1500);
            return true;
        }
        if (cmd.equalsIgnoreCase("power")) {
            setPowered(true);
            return true;
        }
        if (cmd.equalsIgnoreCase("unpower")) {
            setPowered(false);
            return true;
        }
        return false;
    }

    public void explode() {
        player.getWorld().createExplosion(player.getLocation(), getPowered()?6f:3f);
        player.teleport(player.getWorld().getSpawnLocation());
    }

    private boolean getPowered() {
        return datawatcher.getByte(16)==1;
    }

    private void setPowered(boolean b) {
        datawatcher.watch(16,b?1:0);
    }

    private void setExploding(boolean b) {
        datawatcher.watch(17,(b)?1:-1);
    }
}
