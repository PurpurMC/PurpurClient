package net.pl3x.fabric.purpurclient;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PurpurClient implements ModInitializer {
    private static final float DEG2RAD = (float) Math.PI / 180F;
    private static final float HALF_PI = (float) Math.PI / 2F;

    @Override
    public void onInitialize() {
    }

    public static void updatePassengerPosition(Entity entity, Entity passenger, double x, double y, double z, float yaw) {
        if (entity.hasPassenger(passenger)) {
            Vec3d offset = new Vec3d(x, y, z).rotateY(-yaw * DEG2RAD - HALF_PI);
            passenger.setPos(entity.getX() + offset.x, entity.getY() + offset.y, entity.getZ() + offset.z);
        }
    }

    public static void updatePassengerPosition(Entity entity, Entity passenger, Vec3d seat, float yaw) {
        if (entity.hasPassenger(passenger)) {
            double y = entity.getMountedHeightOffset() + passenger.getHeightOffset();
            Vec3d offset = new Vec3d(seat.x, y, seat.z).rotateY(-yaw * DEG2RAD - HALF_PI);
            passenger.setPos(entity.getX() + offset.x, entity.getY() + offset.y, entity.getZ() + offset.z);
        }
    }
}
