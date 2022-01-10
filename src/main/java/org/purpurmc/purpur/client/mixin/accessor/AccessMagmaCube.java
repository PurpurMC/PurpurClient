package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.mob.MagmaCubeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MagmaCubeEntity.class)
public interface AccessMagmaCube {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
