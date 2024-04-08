package org.purpurmc.purpur.client.util;

import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final int PROTOCOL = 0;

    public static final ResourceLocation HELLO = new ResourceLocation("purpur", "client");
    public static final ResourceLocation BEEHIVE_C2S = new ResourceLocation("purpur", "beehive_c2s");
    public static final ResourceLocation BEEHIVE_S2C = new ResourceLocation("purpur", "beehive_s2c");

    public static final float DEG2RAD = (float) Math.PI / 180F;
    public static final float HALF_PI = (float) Math.PI / 2F;
}
