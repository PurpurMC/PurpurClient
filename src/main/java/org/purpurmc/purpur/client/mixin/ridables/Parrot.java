package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParrotEntity.class)
public abstract class Parrot extends LivingEntity implements RidableEntity {
    private final Seat seat = new Seat(-0.15D, 0.3D, 0.0D);

    public Parrot(EntityType<? extends CatEntity> entityType, World world) {
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
