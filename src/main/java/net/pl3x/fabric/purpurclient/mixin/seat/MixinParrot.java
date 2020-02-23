package net.pl3x.fabric.purpurclient.mixin.seat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.world.World;
import net.pl3x.fabric.purpurclient.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParrotEntity.class)
public abstract class MixinParrot extends LivingEntity {
    public MixinParrot(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * 0.3D;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        double x = -0.15D;
        double y = getMountedHeightOffset() + passenger.getHeightOffset();
        PurpurClient.updatePassengerPosition(this, passenger, x, y, 0.0D, bodyYaw);
    }
}
