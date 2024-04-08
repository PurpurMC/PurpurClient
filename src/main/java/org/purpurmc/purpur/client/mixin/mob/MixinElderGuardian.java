package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ElderGuardian.class)
public abstract class MixinElderGuardian extends Mob implements RidableEntity {
    public MixinElderGuardian(EntityType<? extends ElderGuardian> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().elderGuardian.x, getSeats().elderGuardian.y, getSeats().elderGuardian.z);
    }
}
