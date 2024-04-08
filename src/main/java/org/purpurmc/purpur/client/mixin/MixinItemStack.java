package org.purpurmc.purpur.client.mixin;

import org.apache.commons.lang3.text.WordUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(method = "appendEnchantmentNames", at = @At("HEAD"), cancellable = true)
    private static void injectAppendEnchantmentNames(List<Component> tooltip, ListTag enchantments, CallbackInfo ci) {
        for (int i = 0; i < enchantments.size(); ++i) {
            CompoundTag nbtCompound = enchantments.getCompound(i);
            ResourceLocation id = EnchantmentHelper.getEnchantmentId(nbtCompound);
            Enchantment enchantment = BuiltInRegistries.ENCHANTMENT.getOptional(id).orElse(null);
            if (enchantment != null) {
                tooltip.add(enchantment.getFullname(EnchantmentHelper.getEnchantmentLevel(nbtCompound)));
            } else if (id != null) {
                tooltip.add(Component.translatable(WordUtils.capitalizeFully(id.getPath().replace("_", " "))).withStyle(ChatFormatting.GRAY));
            }
        }
        ci.cancel();
    }
}
