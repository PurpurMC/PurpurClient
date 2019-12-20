package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PolarBearEntity.class)
public abstract class MixinPolarBear extends LivingEntity {
    private static final float DEG2RAD = (float) Math.PI / 180F;
    private static final float HALF_PI = (float) Math.PI / 2F;

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
    public void updatePassengerPosition(final Entity passenger) {
        if (hasPassenger(passenger)) {
            double xOffset = warningAnimationProgress > 0.0D ? -1.0D * (warningAnimationProgress / 6.0D) : 0.0D;
            double yOffset = getMountedHeightOffset() + passenger.getHeightOffset();
            Vec3d offset = new Vec3d(xOffset, yOffset, 0.0D).rotateY(-bodyYaw * DEG2RAD - HALF_PI);
            passenger.setPosition(getX() + offset.x, getY() + offset.y, getZ() + offset.z);
        }
    }
}
