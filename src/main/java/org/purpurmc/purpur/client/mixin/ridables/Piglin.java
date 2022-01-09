package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinEntity.class)
public abstract class Piglin extends MobEntity implements RidableEntity {
    public Piglin(EntityType<? extends PiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().getPiglin().y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().getPiglin());
    }

    @Override
    public boolean isAiDisabled() {
        // silly hack to stop piglin from shaking on preview screen
        return ((AbstractPiglin) this).getTimeInOverworld() < 0 || super.isAiDisabled();
    }
}
