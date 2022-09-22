package org.purpurmc.purpur.client.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EnchantmentHelper.class)
public class MixinEnchantmentHelper {
    @ModifyConstant(method = "getLevelFromNbt", constant = @Constant(intValue = 255))
    private static int injectLevelFromNbt(int constant) {
        return Integer.MAX_VALUE;
    }
}
