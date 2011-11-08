package me.desmin88.mobdisguise.disguises;

import me.desmin88.mobdisguise.MobDisguise;
import net.minecraft.server.DataWatcher;

import org.bukkit.entity.Player;

public class DisguiseHandler {
    protected MobDisguise plugin;
    protected Player player;
    protected DataWatcher datawatcher;
    
    public DisguiseHandler(Player pl,MobDisguise p) {
        plugin=p;
        player=pl;
        datawatcher=MobDisguise.data.get(pl);
    }

    public boolean handleEffectCommand(String cmd, String[] arg) {
        // TODO Auto-generated method stub
        return false;
    }
}
