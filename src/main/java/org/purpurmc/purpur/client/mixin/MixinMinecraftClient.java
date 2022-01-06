package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
    @Shadow
    public ClientPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "shouldBlockMessages", cancellable = true)
    public void shouldBlockMessages(UUID sender, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((this.player == null || !sender.equals(this.player.getUuid())) && !sender.equals(Util.NIL_UUID));
    }
}
