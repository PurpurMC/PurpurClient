package org.purpurmc.purpur.client.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
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
            if (damageSource == DamageSource.CACTUS) return true;
            if (damageSource.isFire() || damageSource == DamageSource.IN_FIRE) return true;
            if (damageSource == DamageSource.LIGHTNING_BOLT) return true;
            if (damageSource.isExplosive()) return true;
        }
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean isInLava() {
        return !this.firstUpdate && (this.fluidHeight.getDouble(FluidTags.LAVA) > 0.0D || this.world.getStatesInBoxIfLoaded(this.getBoundingBox().contract(1.0E-6D)).anyMatch((state) -> state.isIn(BlockTags.FIRE)));
    }
}
