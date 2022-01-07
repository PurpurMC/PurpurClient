package org.purpurmc.purpur.client.config;

import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class Config {
    private Seats seats = new Seats();

    @Setting("fix_chat_stutter")
    private boolean fix_chat_stutter = true;
    @Setting("bee_count_in_debug")
    private boolean bee_count_in_debug = true;

    public Seats getSeats() {
        return this.seats;
    }

    public void setSeats(Seats seats) {
        this.seats = seats;
    }

    public boolean isFixChatStutter() {
        return fix_chat_stutter;
    }

    public void setFixChatStutter(boolean fix_chat_stutter) {
        this.fix_chat_stutter = fix_chat_stutter;
        PurpurClient.instance().getConfigManager().save();
    }

    public boolean isBeeCountInDebug() {
        return bee_count_in_debug;
    }

    public void setBeeCountInDebug(boolean bee_count_in_debug) {
        this.bee_count_in_debug = bee_count_in_debug;
        PurpurClient.instance().getConfigManager().save();
    }
}
