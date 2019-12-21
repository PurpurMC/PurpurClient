package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CatEntity.class)
public abstract class MixinCat extends LivingEntity {
    public MixinCat(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return (double) getHeight() * 0.4D;
    }
}
