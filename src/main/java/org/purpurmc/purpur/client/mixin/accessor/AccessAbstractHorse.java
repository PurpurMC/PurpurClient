package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractHorseEntity.class)
public interface AccessAbstractHorse {
    @Accessor
    float getLastAngryAnimationProgress();
}
