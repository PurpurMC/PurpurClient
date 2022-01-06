package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BatEntity.class)
public abstract class Bat extends LivingEntity implements RidableEntity {
    private final Seat seat = new Seat(-0.25D, 0.5D, 0.0D);

    public Bat(EntityType<? extends BatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * this.seat.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, this.seat);
    }
}
