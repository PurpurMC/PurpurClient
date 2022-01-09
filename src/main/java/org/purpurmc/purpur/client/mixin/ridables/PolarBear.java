package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PolarBearEntity.class)
public abstract class PolarBear extends MobEntity implements RidableEntity {
    private final Seat seat = new Seat(0.0D, 0.0D, 0.0D);

    @Shadow
    private float warningAnimationProgress;

    public PolarBear(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        double height = getHeight() * getSeats().polarBear.y;
        if (this.warningAnimationProgress > 0.0F) {
            height -= (getSeats().polarBear.y + 0.5D) * (this.warningAnimationProgress / 6.0F);
        }
        return height;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        this.seat.x = this.warningAnimationProgress > 0.0D ? (getSeats().polarBear.x - 1.0D) * (this.warningAnimationProgress / 6.0D) : getSeats().polarBear.x;
        this.seat.y = getSeats().polarBear.y;
        this.seat.z = getSeats().polarBear.z;
        updatePassengerPosition(passenger, this.seat);
    }
}
