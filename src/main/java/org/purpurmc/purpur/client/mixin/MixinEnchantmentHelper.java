package org.purpurmc.purpur.client.mixin;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {
    @ModifyConstant(method = "getEnchantmentLevel", constant = @Constant(intValue = 255))
    private static int injectEnchantmentLevel(int constant) {
        return Integer.MAX_VALUE;
    }
}
