package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinBruteEntity.class)
public abstract class MixinPiglinBrute extends MobEntity implements RidableEntity {
    public MixinPiglinBrute(EntityType<? extends PiglinBruteEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().piglinBrute.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().piglinBrute);
    }

    @Override
    public boolean isAiDisabled() {
        // silly hack to stop piglin brute from shaking on preview screen
        return ((AccessAbstractPiglin) this).getTimeInOverworld() < 0 || super.isAiDisabled();
    }
}
