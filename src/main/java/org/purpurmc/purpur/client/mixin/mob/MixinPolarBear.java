package org.purpurmc.purpur.client.mixin.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearEntity.class)
public abstract class MixinPolarBear extends MobEntity implements RidableEntity {
    @Unique
    private final Seat seat = new Seat(0.0D, 0.0D, 0.0D);

    @Shadow
    private float warningAnimationProgress;

    public MixinPolarBear(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return super.getPassengerAttachmentPos(passenger, dimensions, scaleFactor).add(0, dimensions.height + (float) getSeats().polarBear.y, 0);
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        this.seat.x = this.warningAnimationProgress > 0.0D ? (getSeats().polarBear.x - 1.0D) * (this.warningAnimationProgress / 6.0D) : getSeats().polarBear.x;
        this.seat.y = getSeats().polarBear.y;
        this.seat.z = getSeats().polarBear.z;
        updatePassengerPosition(passenger, this.seat);
    }
}
