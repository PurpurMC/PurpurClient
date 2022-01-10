package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessHoglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoglinEntity.class)
public abstract class MixinHoglin extends MobEntity implements RidableEntity {
    public MixinHoglin(EntityType<? extends HoglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().hoglin.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().hoglin);
    }

    @Override
    public boolean isAiDisabled() {
        // silly hack to stop hoglin from shaking on preview screen
        return ((AccessHoglin) this).getTimeInOverworld() < 0 || super.isAiDisabled();
    }
}
