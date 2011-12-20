package me.desmin88.mobdisguise.disguises;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.entity.Player;

public class CreeperHandler extends DisguiseHandler {
    Timer explodeTimer = new Timer();
    
    public CreeperHandler(final Player pl, final MobDisguise p) {
        super(pl, p, MobIdEnum.CREEPER.id);
        datawatcher.a(16, Byte.valueOf((byte) -1));
        datawatcher.a(17, Byte.valueOf((byte) 0));
        Logger.getLogger("Minecraft").info(pl.getName() + " has been disguised as a creeper.");
    }
    
    class ExplodeTask extends TimerTask {
        @Override
        public void run() {
            try {
                setExploding(false);
                explode();
            } catch (final Exception e) {
                return;
            }
        }
    }
    
    @Override
    public boolean handleEffectCommand(final String cmd, final String[] arg) {
        if (cmd.equalsIgnoreCase("explode")) {
            if (datawatcher == null) {
                player.sendMessage("Sorry, it appears that the datawatcher we need is null.");
                return true;
            }
            setExploding(true);
            explodeTimer.schedule(new ExplodeTask(), 1500);
            player.sendMessage(MobDisguise.pref + "You have been set to explode!");
            return true;
        }
        if (cmd.equalsIgnoreCase("power")) {
            if (datawatcher == null) {
                player.sendMessage("Sorry, it appears that the datawatcher we need is null.");
                return true;
            }
            setPowered(true);
            player.sendMessage(MobDisguise.pref + "You have been powered!");
            return true;
        }
        if (cmd.equalsIgnoreCase("unpower")) {
            if (datawatcher == null) {
                player.sendMessage("Sorry, it appears that the datawatcher we need is null.");
                return true;
            }
            setPowered(false);
            player.sendMessage(MobDisguise.pref + "You have been unpowered!");
            return true;
        }
        player.sendMessage(MobDisguise.pref + "/md e explode|power|unpower");
        return false;
    }
    
    public void explode() {
        player.getWorld().createExplosion(player.getLocation(), isPowered() ? 6f : 3f);
        player.teleport(player.getWorld().getSpawnLocation());
    }
    
    public boolean isPowered() {
        return datawatcher.getByte(17) == 1;
    }
    
    private void setPowered(final boolean b) {
        datawatcher.watch(17, Byte.valueOf(b ? (byte) 1 : (byte) 0));
        sendUpdate();
    }
    
    private void setExploding(final boolean b) {
        datawatcher.watch(16, Byte.valueOf((b) ? (byte) 1 : (byte) -1));
        sendUpdate();
    }
}
