package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HappyGhast.class)
public abstract class MixinHappyGhast extends Mob implements RidableEntity {
    public MixinHappyGhast(EntityType<? extends HappyGhast> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().happyGhast.x, getSeats().happyGhast.y, getSeats().happyGhast.z);
    }
}
