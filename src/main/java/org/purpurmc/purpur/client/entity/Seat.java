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

    public void setSeat(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
