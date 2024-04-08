package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Slime.class)
public interface AccessSlime {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
