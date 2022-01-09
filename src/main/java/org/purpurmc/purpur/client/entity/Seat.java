package org.purpurmc.purpur.client.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.purpurmc.purpur.client.util.Constants;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class Seat {
    public double x;
    public double y;
    public double z;

    // no-args ctor for configurate
    @SuppressWarnings("unused")
    public Seat() {
        this(0, 0, 0);
    }

    public Seat(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d rotate(LivingEntity entity, Entity passenger) {
        float angle = -entity.bodyYaw * Constants.DEG2RAD - Constants.HALF_PI;
        double cos = MathHelper.cos(angle);
        double sin = MathHelper.sin(angle);

        double x = (this.x * cos + this.z * sin) + entity.getX();
        double z = (this.z * cos - this.x * sin) + entity.getZ();
        double y = entity.getY() + entity.getMountedHeightOffset() + passenger.getHeightOffset();

        return new Vec3d(x, y, z);
    }
}
