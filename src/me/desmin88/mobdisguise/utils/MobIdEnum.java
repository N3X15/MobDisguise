package me.desmin88.mobdisguise.utils;

import java.lang.reflect.InvocationTargetException;

import me.desmin88.mobdisguise.MobDisguise;
import me.desmin88.mobdisguise.disguises.CreeperHandler;
import me.desmin88.mobdisguise.disguises.DisguiseHandler;

import org.bukkit.entity.Player;

public enum MobIdEnum {
    //STEVE(49),
    CREEPER(50,CreeperHandler.class),
    SKELETON(51),
    SPIDER(52),
    GIANT(53),
    ZOMBIE(54),
    SLIME(55),
    GHAST(56),
    PIGMAN(57),
    ENDERMAN(58),
    CAVESPIDER(59),
    SILVERFISH(60),
    PIG(90),
    SHEEP(91),
    COW(92),
    CHICKEN(93),
    SQUID(94),
    WOLF(95),
    ;
    
    public byte id;
    private Class<? extends DisguiseHandler> handlerClass=DisguiseHandler.class;

    MobIdEnum(int id) {
        this.id=(byte)id;
    }
    MobIdEnum(int id,Class<? extends DisguiseHandler> handler) {
        this.id=(byte)id;
        this.handlerClass=handler;
    }
    
    public DisguiseHandler instantiate(Player p,MobDisguise plugin) {
        try {
            return handlerClass.getConstructor(Player.class,MobDisguise.class).newInstance(p,plugin);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public static String types = "creeper,skeleton,spider,giant,zombie,slime,pigman,pig,sheep,cow,chicken,squid,wolf,enderman,cavespider,silverfish";

    public static String getTypeFromByte(Integer in) {
        for(MobIdEnum s : values()) {
            if(s.id == in) {
                return s.name().toLowerCase();
            }
        }
        return null;
    }
    public static MobIdEnum getFromByte(Integer in) {
        for(MobIdEnum s : values()) {
            if(s.id == in) {
                return s;
            }
        }
        return null;
    }
    public static MobIdEnum get(String mobtype) {
        for(MobIdEnum s : values()) {
            if(s.name().equalsIgnoreCase(mobtype)) {
                return s;
            }
        }
        return null;
    }
}
