package net.pl3x.fabric.purpurclient.mixin.seat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import net.pl3x.fabric.purpurclient.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PolarBearEntity.class)
public abstract class MixinPolarBear extends LivingEntity {
    @Shadow
    private float warningAnimationProgress;

    public MixinPolarBear(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        double height = getHeight() * 0.75D;
        if (warningAnimationProgress > 0.0F) {
            height -= 0.5 * (warningAnimationProgress / 6.0F);
        }
        return height;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        double x = warningAnimationProgress > 0.0D ? -1.0D * (warningAnimationProgress / 6.0D) : 0.0D;
        double y = getMountedHeightOffset() + passenger.getHeightOffset();
        PurpurClient.updatePassengerPosition(this, passenger, x, y, 0.0D, bodyYaw);
    }
}
