package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pl3x.fabric.purpurclient.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IronGolemEntity.class)
public abstract class MixinIronGolem extends LivingEntity {
    private final Vec3d offset = new Vec3d(-0.25D, 0.7D, 0.0D);

    public MixinIronGolem(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * offset.y;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        PurpurClient.updatePassengerPosition(this, passenger, offset, bodyYaw);
    }
}
