package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Breeze.class)
public abstract class MixinCamel extends Mob implements RidableEntity {
    public MixinCamel(EntityType<? extends Camel> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return super.getPassengerRidingPosition(passenger).add(getSeats().camel.x, getSeats().camel.y, getSeats().camel.z);
    }

    @Override
    public boolean isNoAi() {
        // silly hack to stop camel from standing up on preview screen
        return ((AccessAbstractPiglin) this).getTimeInOverworld() < 0 || super.isNoAi();
    }
}
