package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieHorseEntity.class)
public abstract class MixinZombieHorse extends MobEntity implements RidableEntity {
    public MixinZombieHorse(EntityType<? extends ZombieHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().zombieHorse.x, getSeats().zombieHorse.y, getSeats().zombieHorse.z);
    }
}
