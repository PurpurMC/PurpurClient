package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CatEntity.class)
public abstract class MixinCat extends MobEntity implements RidableEntity {
    public MixinCat(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().cat.x, getSeats().cat.y, getSeats().cat.z);
    }
}
