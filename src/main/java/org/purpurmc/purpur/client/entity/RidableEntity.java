package org.purpurmc.purpur.client.entity;

import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.Seats;

public interface RidableEntity {
    default Seats getSeats() {
        return PurpurClient.instance().getConfig().seats;
    }
}
