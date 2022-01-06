package org.purpurmc.purpur.client.util;

import net.minecraft.util.Identifier;

public class Constants {
    public static final int PROTOCOL = 0;

    public static final Identifier HELLO = new Identifier("purpur", "client");
    public static final Identifier BEEHIVE_C2S = new Identifier("purpur", "beehive_c2s");
    public static final Identifier BEEHIVE_S2C = new Identifier("purpur", "beehive_s2c");

    public static final float DEG2RAD = (float) Math.PI / 180F;
    public static final float HALF_PI = (float) Math.PI / 2F;
}
