package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DrownedEntity.class)
public abstract class MixinDrowned extends MobEntity implements RidableEntity {
    public MixinDrowned(EntityType<? extends DrownedEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().drowned.x, getSeats().drowned.y, getSeats().drowned.z);
    }
}
