package org.purpurmc.purpur.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.purpurmc.purpur.client.util.RenderSystemMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static com.mojang.blaze3d.systems.RenderSystem.isOnRenderThread;
import static com.mojang.blaze3d.systems.RenderSystem.recordRenderCall;

@Mixin(RenderSystem.class)
public class MixinRenderSystem implements RenderSystemMixin {

    @Unique
    private static Matrix4f modelViewMatrix;

    @Final
    @Shadow
    private static Matrix4fStack modelViewStack;

    @Unique
    public void purpurClient$applyModelViewMatrix() {
        Matrix4f matrix4f = new Matrix4f(modelViewStack);
        if (!isOnRenderThread()) {
            recordRenderCall(() -> modelViewMatrix = matrix4f);
        } else {
            modelViewMatrix = matrix4f;
        }
    }
}

