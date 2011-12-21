package me.desmin88.mobdisguise.utils;

import java.lang.reflect.InvocationTargetException;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.disguises.CreeperHandler;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;
import me.desmin88.mobdisguise.disguises.GhastHandler;
import me.desmin88.mobdisguise.disguises.PlayerHandler;

import org.bukkit.entity.Player;

public enum MobIdEnum {
    //STEVE(49),
    CREEPER(50, CreeperHandler.class),
    SKELETON(51),
    SPIDER(52),
    GIANT(53),
    ZOMBIE(54),
    SLIME(55),
    GHAST(56, GhastHandler.class),
    PIGMAN(57),
    ENDERMAN(58),
    CAVESPIDER(59),
    SILVERFISH(60),
    BLAZE(61),
    LAVA_SLIME(62),
    ENDER_DRAGON(63),
    PIG(90),
    SHEEP(91),
    COW(92),
    CHICKEN(93),
    SQUID(94),
    WOLF(95),
    MUSHROOM_COW(96),
    SNOWMAN(97),
    VILLAGER(120),
    
    PLAYER(-1, PlayerHandler.class);
    
    public byte id;
    private Class<? extends DisguiseHandler> handlerClass = DisguiseHandler.class;
    
    MobIdEnum(final int id) {
        this.id = (byte) id;
    }
    
    MobIdEnum(final int id, final Class<? extends DisguiseHandler> handler) {
        this.id = (byte) id;
        handlerClass = handler;
    }
    
    public DisguiseHandler instantiate(final Player p, final MobDisguise plugin) {
        try {
            if (handlerClass.equals(DisguiseHandler.class)) {
                return handlerClass.getConstructor(Player.class, MobDisguise.class, byte.class).newInstance(p, plugin, this.id);
            } else {
                return handlerClass.getConstructor(Player.class, MobDisguise.class).newInstance(p, plugin);
            }
        } catch (final IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getTypeFromByte(final Integer in) {
        for (final MobIdEnum s : values()) {
            if (s.id == in)
                return s.name().toLowerCase();
        }
        return null;
    }
    
    public static MobIdEnum getFromByte(final Integer in) {
        for (final MobIdEnum s : values()) {
            if (s.id == in)
                return s;
        }
        return null;
    }
    
    public static MobIdEnum get(final String mobtype) {
        for (final MobIdEnum s : values()) {
            if (s.name().equalsIgnoreCase(mobtype))
                return s;
        }
        return null;
    }
}
