package org.purpurmc.purpur.client.mixin.ridables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.purpurmc.purpur.client.entity.Seat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OcelotEntity.class)
public abstract class Ocelot extends LivingEntity implements RidableEntity {
    private final Seat seat = new Seat(0.0D, 0.5D, 0.0D);

    public Ocelot(EntityType<? extends OcelotEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * this.seat.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(passenger, this.seat);
    }
}
