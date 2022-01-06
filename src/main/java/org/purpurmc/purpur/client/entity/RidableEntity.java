package org.purpurmc.purpur.client.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public interface RidableEntity {
    default void updatePassengerPosition(Entity passenger, Seat seat) {
        LivingEntity entity = (LivingEntity) this;
        if (entity.hasPassenger(passenger)) {
            Vec3d vec = seat.rotate(entity, passenger);
            passenger.setPos(vec.x, vec.y, vec.z);
        }
    }
}
