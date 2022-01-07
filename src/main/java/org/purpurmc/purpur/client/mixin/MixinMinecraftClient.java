package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.util.Util;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Final
    @Shadow
    private SocialInteractionsManager socialInteractionsManager;
    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    public MinecraftClient.ChatRestriction getChatRestriction() {
        return null;
    }

    @Inject(at = @At("HEAD"), method = "shouldBlockMessages", cancellable = true)
    public void shouldBlockMessages(UUID sender, CallbackInfoReturnable<Boolean> cir) {
        if (PurpurClient.instance().getConfig().isFixChatStutter()) {
            cir.setReturnValue(false);
        } else {
            if (this.getChatRestriction().allowsChat(false)) {
                cir.setReturnValue(this.socialInteractionsManager.isPlayerMuted(sender));
            } else {
                cir.setReturnValue((this.player == null || !sender.equals(this.player.getUuid())) && !sender.equals(Util.NIL_UUID));
            }
        }
    }
}
