package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.render.debug.GameTestDebugRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GameTestDebugRenderer.class)
public class GameTestDebugRendererMixin {
    @ModifyArgs(method = "renderMarker", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"))
    private void injected(Args args) {
        // show full colors, not just green
        args.set(0, 1.0F); // red
        args.set(1, 1.0F); // green
        args.set(2, 1.0F); // blue
        args.set(3, 0.75F); // alpha
    }
}
