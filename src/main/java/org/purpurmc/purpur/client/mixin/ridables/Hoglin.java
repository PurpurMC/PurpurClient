package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoglinEntity.class)
public abstract class Hoglin extends MobEntity implements RidableEntity {
    public Hoglin(EntityType<? extends HoglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().getHoglin().y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().getHoglin());
    }

    @Override
    public boolean isAiDisabled() {
        // silly hack to stop hoglin from shaking on preview screen
        return ((org.purpurmc.purpur.client.mixin.accessor.Hoglin) this).getTimeInOverworld() < 0 || super.isAiDisabled();
    }
}
