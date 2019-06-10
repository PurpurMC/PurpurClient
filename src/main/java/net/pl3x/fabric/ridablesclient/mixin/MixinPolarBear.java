package net.pl3x.fabric.ridablesclient.mixin;

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
            float yOffset = (float) (getMountedHeightOffset() + passenger.getHeightOffset());

            float xOffset = 0.0F;
            if (warningAnimationProgress > 0.0F) {
                xOffset = -1.0f * (warningAnimationProgress / 6.0F);
            }

            Vec3d offset = new Vec3d(xOffset, 0.0, 0.0).rotateY(-field_6283 * 0.017453292F - 1.5707964F);

            passenger.setPosition(x + offset.x, y + yOffset, z + offset.z);
        }
    }
}
