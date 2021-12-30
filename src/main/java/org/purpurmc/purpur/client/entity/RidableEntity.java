package org.purpurmc.purpur.client.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.purpurmc.purpur.client.util.Constants;

public interface RidableEntity {
    default void updatePassengerPosition(LivingEntity entity, Entity passenger, Vec3d seat, float yaw) {
        if (entity.hasPassenger(passenger)) {
            double y = entity.getMountedHeightOffset() + passenger.getHeightOffset();
            Vec3d offset = new Vec3d(seat.x, y, seat.z).rotateY(-yaw * Constants.DEG2RAD - Constants.HALF_PI);
            passenger.setPos(entity.getX() + offset.x, entity.getY() + offset.y, entity.getZ() + offset.z);
        }
    }
}
