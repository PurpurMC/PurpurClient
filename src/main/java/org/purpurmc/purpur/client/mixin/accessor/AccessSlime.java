package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlimeEntity.class)
public interface AccessSlime {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
