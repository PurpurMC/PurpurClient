package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ElderGuardianEntity.class)
public abstract class ElderGuardian extends LivingEntity implements RidableEntity {
    public ElderGuardian(EntityType<? extends ElderGuardianEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().getElderGuardian().y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().getElderGuardian());
    }
}
