package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PolarBearEntity.class)
public abstract class MixinPolarBear extends LivingEntity implements RidableEntity {
    private final Vec3d offset = new Vec3d(0.0D, 0.75D, 0.0D);
    private final Vec3d offsetStanding = new Vec3d(-1.0D, 0.5D, 0.0D);

    @Shadow
    private float warningAnimationProgress;

    public MixinPolarBear(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        double height = getHeight() * offset.y;
        if (warningAnimationProgress > 0.0F) {
            height -= offsetStanding.y * (warningAnimationProgress / 6.0F);
        }
        return height;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        double x = warningAnimationProgress > 0.0D ? offsetStanding.x * (warningAnimationProgress / 6.0D) : offset.x;
        updatePassengerPosition(this, passenger, new Vec3d(x, offset.y, offset.z), bodyYaw);
    }
}
