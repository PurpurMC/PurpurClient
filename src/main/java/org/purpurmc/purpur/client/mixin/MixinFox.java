package org.purpurmc.purpur.client.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.purpurmc.purpur.client.entity.RidableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FoxEntity.class)
public abstract class MixinFox extends LivingEntity implements RidableEntity {
    private final Vec3d offset = new Vec3d(-0.25D, 0.6D, 0.0D);

    public MixinFox(EntityType<? extends FoxEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * offset.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        updatePassengerPosition(this, passenger, offset, bodyYaw);
    }
}
