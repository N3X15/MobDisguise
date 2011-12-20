package me.desmin88.mobdisguise.disguises;

public enum DataWatcherField {
    FLAGS(0, "Flags", int.class), // 0 (INT)
    AIR_TICKS(1, "AirTicks", int.class),
    POTION_EFFECTS(8, "PotionEffects", int.class),
    AGE(12, "Age", int.class);
    
    public int id = 0;
    public String name = "";
    public Class<?> type = null;
    
    DataWatcherField(final int id, final String name, final Class<?> type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
