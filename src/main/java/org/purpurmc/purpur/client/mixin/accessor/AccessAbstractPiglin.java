package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractPiglin.class)
public interface AccessAbstractPiglin {
    @Accessor
    int getTimeInOverworld();

    @Accessor("timeInOverworld")
    void setTimeInOverworld(int time);
}
