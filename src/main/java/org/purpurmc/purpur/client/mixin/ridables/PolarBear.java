package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PolarBearEntity.class)
public abstract class PolarBear extends LivingEntity implements RidableEntity {
    private final Seat seatNormal = new Seat(0.0D, 0.75D, 0.0D);
    private final Seat seatStanding = new Seat(-1.0D, 0.5D, 0.0D);

    private final Seat seat = new Seat(0.0D, 0.0D, 0.0D);

    @Shadow
    private float warningAnimationProgress;

    public PolarBear(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        double height = getHeight() * this.seatNormal.y;
        if (this.warningAnimationProgress > 0.0F) {
            height -= this.seatStanding.y * (this.warningAnimationProgress / 6.0F);
        }
        return height;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        this.seat.x = this.warningAnimationProgress > 0.0D ? this.seatStanding.x * (this.warningAnimationProgress / 6.0D) : this.seatNormal.x;
        this.seat.y = this.seatNormal.y;
        this.seat.z = this.seatNormal.z;
        updatePassengerPosition(passenger, this.seat);
    }
}
