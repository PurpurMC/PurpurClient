package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    public MixinItemEntity(EntityType<? extends ItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (world.isClient) {
            if (damageSource == DamageSource.LAVA) return false;
            if (damageSource.isFire() || damageSource == DamageSource.IN_FIRE) return false;
            if (damageSource.isExplosive()) return false;
        }
        return super.isInvulnerableTo(damageSource);
    }
}
