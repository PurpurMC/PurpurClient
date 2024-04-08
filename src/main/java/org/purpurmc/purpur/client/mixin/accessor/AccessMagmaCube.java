package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.world.entity.monster.MagmaCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MagmaCube.class)
public interface AccessMagmaCube {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
