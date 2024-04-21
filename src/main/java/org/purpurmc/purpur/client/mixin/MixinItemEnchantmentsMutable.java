package org.purpurmc.purpur.client.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.Mutable.class)
public class MixinItemEnchantmentsMutable {
    @ModifyConstant(method = "set", constant = @Constant(intValue = 255))
    private static int injectSet(int constant) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "upgrade", constant = @Constant(intValue = 255))
    private static int injectUpgrade(int constant) {
        return Integer.MAX_VALUE;
    }
}
