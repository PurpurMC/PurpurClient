package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.mob.AbstractPiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractPiglinEntity.class)
public interface AccessAbstractPiglin {
    @Accessor
    int getTimeInOverworld();

    @Accessor("timeInOverworld")
    void setTimeInOverworld(int time);
}
