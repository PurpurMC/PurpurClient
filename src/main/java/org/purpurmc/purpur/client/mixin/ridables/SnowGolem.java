package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolem extends LivingEntity implements RidableEntity {
    public SnowGolem(EntityType<? extends SnowGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().getSnowGolem().y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().getSnowGolem());
    }
}
