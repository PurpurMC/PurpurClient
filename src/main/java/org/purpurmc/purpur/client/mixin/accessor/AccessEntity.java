package org.purpurmc.purpur.client.mixin.accessor;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface AccessEntity {
    @Accessor("firstTick")
    void setFirstTick(boolean bool);

    @Accessor("wasTouchingWater")
    void setWasTouchingWater(boolean bool);
}
