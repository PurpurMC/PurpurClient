package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitherSkeletonEntity.class)
public abstract class MixinWitherSkeleton extends MobEntity implements RidableEntity {
    public MixinWitherSkeleton(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().witherSkeleton.x, getSeats().witherSkeleton.y, getSeats().witherSkeleton.z);
    }
}
