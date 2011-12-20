package me.desmin88.mobdisguise.disguises;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.utils.MobIdEnum;

import org.bukkit.entity.Player;

public class PlayerHandler extends DisguiseHandler {
    
    public PlayerHandler(final Player pl, final MobDisguise p, final String name) {
        super(pl, p, MobIdEnum.PLAYER.id);
        MobDisguise.pu.disguisep2pToAll(pl, name);
    }
    
}
