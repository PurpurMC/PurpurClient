package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinBrute.class)
public abstract class MixinPiglinBrute extends Mob implements RidableEntity {
    public MixinPiglinBrute(EntityType<? extends PiglinBrute> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().piglinBrute.x, getSeats().piglinBrute.y, getSeats().piglinBrute.z);
    }

    @Override
    public boolean isNoAi() {
        // silly hack to stop piglin brute from shaking on preview screen
        return ((AccessAbstractPiglin) this).getTimeInOverworld() < 0 || super.isNoAi();
    }
}
