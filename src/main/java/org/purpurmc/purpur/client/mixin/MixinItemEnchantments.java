package org.purpurmc.purpur.client.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.class)
public class MixinItemEnchantments {
    // TODO: modify static LEVEL_CODEC & FULL_CODEC

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 255))
    private static int injectConstructor(int constant) {
        return Integer.MAX_VALUE;
    }
}
