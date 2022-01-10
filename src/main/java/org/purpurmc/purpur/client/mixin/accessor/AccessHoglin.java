package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.mob.HoglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HoglinEntity.class)
public interface AccessHoglin {
    @Accessor
    int getTimeInOverworld();

    @Accessor("timeInOverworld")
    void setTimeInOverworld(int time);
}
