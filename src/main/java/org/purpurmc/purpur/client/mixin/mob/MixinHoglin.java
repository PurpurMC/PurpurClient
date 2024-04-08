package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessHoglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Hoglin.class)
public abstract class MixinHoglin extends Mob implements RidableEntity {
    public MixinHoglin(EntityType<? extends Hoglin> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().hoglin.x, getSeats().hoglin.y, getSeats().hoglin.z);
    }

    @Override
    public boolean isNoAi() {
        // silly hack to stop hoglin from shaking on preview screen
        return ((AccessHoglin) this).getTimeInOverworld() < 0 || super.isNoAi();
    }
}
