package org.purpurmc.purpur.client.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.Seats;

public interface RidableEntity {
    default void updatePassengerPosition(Entity passenger, Seat seat) {
        LivingEntity entity = (LivingEntity) this;
        if (entity.hasPassenger(passenger)) {
            Vec3d vec = seat.rotate(entity, passenger);
            passenger.setPosition(vec.x, vec.y, vec.z);
        }
    }

    default Seats getSeats() {
        return PurpurClient.instance().getConfig().seats;
    }
}
