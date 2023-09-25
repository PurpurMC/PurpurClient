package org.purpurmc.purpur.client.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
@SuppressWarnings("CanBeFinal")
public class Config {
    public int configVersion = 1;
    public boolean beeCountInDebug = true;
    public boolean useSplashScreen = true;
    public boolean useWindowTitle = true;

    public Seats seats = new Seats();
}
