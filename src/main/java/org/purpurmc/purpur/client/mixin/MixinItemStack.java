package org.purpurmc.purpur.client.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "appendEnchantments", at = @At("HEAD"), cancellable = true)
    private static void injectAppendEnchantments(List<Text> tooltip, NbtList enchantments, CallbackInfo ci) {
        for (int i = 0; i < enchantments.size(); ++i) {
            NbtCompound nbtCompound = enchantments.getCompound(i);
            Identifier id = EnchantmentHelper.getIdFromNbt(nbtCompound);
            Enchantment enchantment = Registries.ENCHANTMENT.getOrEmpty(id).orElse(null);
            if (enchantment != null) {
                tooltip.add(enchantment.getName(EnchantmentHelper.getLevelFromNbt(nbtCompound)));
            } else if (id != null) {
                tooltip.add(Text.translatable(WordUtils.capitalizeFully(id.getPath().replace("_", " "))).formatted(Formatting.GRAY));
            }
        }
        ci.cancel();
    }
}
