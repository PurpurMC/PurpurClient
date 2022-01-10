package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface AccessEntity {
    @Accessor("firstUpdate")
    void setFirstUpdate(boolean bool);

    @Accessor("touchingWater")
    void setTouchingWater(boolean bool);
}
