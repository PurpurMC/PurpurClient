package org.purpurmc.purpur.client.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.class)
public class MixinItemEnchantments {
    @Shadow
    private static final Codec<Integer> LEVEL_CODEC = Codec.intRange(0, Integer.MAX_VALUE);

    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 255))
    private int injectConstructor(int constant) {
        return Integer.MAX_VALUE;
    }

    @Mixin(ItemEnchantments.Mutable.class)
    public static class MixinItemEnchantmentsMutable {
        @ModifyConstant(method = "set", constant = @Constant(intValue = 255))
        private int injectSet(int constant) {
            return Integer.MAX_VALUE;
        }

        @ModifyConstant(method = "upgrade", constant = @Constant(intValue = 255))
        private int injectUpgrade(int constant) {
            return Integer.MAX_VALUE;
        }
    }
}
