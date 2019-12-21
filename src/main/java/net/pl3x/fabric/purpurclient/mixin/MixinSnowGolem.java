package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import net.pl3x.fabric.purpurclient.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowGolemEntity.class)
public abstract class MixinSnowGolem extends LivingEntity {
    public MixinSnowGolem(EntityType<? extends SnowGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * 0.6D;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        double x = -0.3D;
        double y = getMountedHeightOffset() + passenger.getHeightOffset();
        PurpurClient.updatePassengerPosition(this, passenger, x, y, 0.0D, bodyYaw);
    }
}
