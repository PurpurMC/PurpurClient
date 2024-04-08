package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBear.class)
public abstract class MixinPolarBear extends Mob implements RidableEntity {
    @Unique
    private final Seat seat = new Seat(0.0D, 0.0D, 0.0D);

    @Shadow
    private float clientSideStandAnimation;

    public MixinPolarBear(EntityType<? extends PolarBear> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().polarBear.x, getSeats().polarBear.y, getSeats().polarBear.z);
    }
}
