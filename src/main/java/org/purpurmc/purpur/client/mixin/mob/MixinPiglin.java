package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinEntity.class)
public abstract class MixinPiglin extends MobEntity implements RidableEntity {
    public MixinPiglin(EntityType<? extends PiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(getSeats().piglin.x, getSeats().piglin.y, getSeats().piglin.z);
    }

    @Override
    public boolean isAiDisabled() {
        // silly hack to stop piglin from shaking on preview screen
        return ((AccessAbstractPiglin) this).getTimeInOverworld() < 0 || super.isAiDisabled();
    }
}
