package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BatEntity.class)
public abstract class Bat extends LivingEntity implements RidableEntity {
    public Bat(EntityType<? extends BatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().getBat().y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().getBat());
    }
}
