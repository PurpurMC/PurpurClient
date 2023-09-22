package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WanderingTraderEntity.class)
public abstract class MixinWanderingTrader extends MobEntity implements RidableEntity {
    public MixinWanderingTrader(EntityType<? extends WanderingTraderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().wanderingTrader.x, getSeats().wanderingTrader.y, getSeats().wanderingTrader.z);
    }
}
