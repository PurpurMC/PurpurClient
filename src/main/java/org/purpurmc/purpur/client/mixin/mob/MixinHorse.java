package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractHorse;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HorseEntity.class)
public abstract class MixinHorse extends MobEntity implements RidableEntity {
    public MixinHorse(EntityType<? extends HorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * getSeats().horse.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, getSeats().horse);

        if (passenger instanceof MobEntity mobEntity) {
            this.bodyYaw = mobEntity.bodyYaw;
        }

        if (((AccessAbstractHorse) this).getLastAngryAnimationProgress() > 0.0F) {
            float f = MathHelper.sin(this.bodyYaw * 0.017453292F);
            float g = MathHelper.cos(this.bodyYaw * 0.017453292F);
            float h = 0.7F * ((AccessAbstractHorse) this).getLastAngryAnimationProgress();
            float i = 0.15F * ((AccessAbstractHorse) this).getLastAngryAnimationProgress();
            passenger.setPosition(this.getX() + (h * f), this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset() + i, this.getZ() - (h * g));
            if (passenger instanceof LivingEntity livingEntity) {
                livingEntity.bodyYaw = this.bodyYaw;
            }
        }

    }
}
