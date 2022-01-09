package org.purpurmc.purpur.client.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(net.minecraft.entity.Entity.class)
public interface Entity {
    @Accessor("firstUpdate")
    void setFirstUpdate(boolean bool);

    @Accessor("touchingWater")
    void setTouchingWater(boolean bool);
}
