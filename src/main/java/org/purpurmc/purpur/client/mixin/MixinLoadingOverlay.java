package org.purpurmc.purpur.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.gui.SplashTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.Mth;

@Mixin(LoadingOverlay.class)
public class MixinLoadingOverlay {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    @Final
    private boolean fadeIn;
    @Shadow
    @Final
    private ReloadInstance reload;
    @Shadow
    private float currentProgress;
    @Shadow
    private long fadeOutStart;
    @Shadow
    private long fadeInStart;
    @Shadow
    @Final
    private Consumer<Optional<Throwable>> onFinish;

    @Unique
    private float delta;

    @Inject(method = "registerTextures", at = @At("HEAD"))
    private static void registerTextures(Minecraft client, CallbackInfo ci) {
        client.getTextureManager().register(SplashTexture.SPLASH, new SplashTexture());
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!PurpurClient.instance().getConfig().useSplashScreen) {
            return;
        }

        ci.cancel();

        int width = this.minecraft.getWindow().getGuiScaledWidth();
        int height = this.minecraft.getWindow().getGuiScaledHeight();

        long curTime = Util.getMillis();
        if (this.fadeIn && this.fadeInStart == -1L) {
            this.fadeInStart = curTime;
        }
        float f = this.fadeOutStart > -1L ? (float) (curTime - this.fadeOutStart) / 1000.0f : -1.0f;
        float g = this.fadeInStart > -1L ? (float) (curTime - this.fadeInStart) / 500.0f : -1.0f;
        float opacity;

        if (f >= 1.0f) {
            if (this.minecraft.screen != null) {
                this.minecraft.screen.render(context, 0, 0, delta);
            }
            this.delta = 0;
            opacity = 1.0f - Mth.clamp(f - 1.0f, 0.0f, 1.0f);
        } else if (this.fadeIn) {
            if (this.minecraft.screen != null && g < 1.0f) {
                this.minecraft.screen.render(context, mouseX, mouseY, delta);
            }
            this.delta = 0;
            opacity = Mth.clamp(g, 0.0f, 1.0f);
        } else {
            this.delta += this.minecraft.getDeltaTracker().getRealtimeDeltaTicks();
            opacity = Mth.clampedLerp(-0.5F, 1.0F, this.delta / 30F);
        }

        RenderSystem.setShaderTexture(0, SplashTexture.SPLASH);
        RenderSystem.enableBlend();
        // TODO: Currently not working because the shader is not loaded yet I guess?
//        RenderSystem.setShader(CoreShaders.POSITION_TEX);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        Function<ResourceLocation, RenderType> renderTypeFunction = RenderType::guiTextured;
        context.blit(renderTypeFunction, SplashTexture.SPLASH, 0, 0, width, height, 0, 0, 1024, 544, 1024, 1024); // background
        context.blit(renderTypeFunction, SplashTexture.SPLASH, (int) ((width - 112) * Mth.lerp(easeOut(opacity), 0.8D, 1.0D)), 10, 112, 112, 0, 546, 256, 256, 1024, 1024); // logo
        context.blit(renderTypeFunction, SplashTexture.SPLASH, 40 + (int) ((20) * -Mth.lerp(easeOut(opacity), 0.0D, 1.0D)), height - 70, 180, 17, 256, 548, 367, 33, 1024, 1024); // slogan
        context.blit(renderTypeFunction, SplashTexture.SPLASH, (int) ((20) * Mth.lerp(easeOut(opacity), -1.0D, 1.0D)), height - 50, 100, 30, 256, 587, 210, 61, 1024, 1024); // Purpur
        context.blit(renderTypeFunction, SplashTexture.SPLASH, width - 105, (int) ((height - 15) * Mth.lerp(easeOut(opacity), 0.75D, 1.0D)), 100, 12, 256, 658, 200, 23, 1024, 1024); // url
        RenderSystem.disableBlend();

        int scale = (int) ((double) this.minecraft.getWindow().getGuiScaledHeight() * 0.625D);
        float reloadProgress = this.reload.getActualProgress();
        this.currentProgress = Mth.clamp(this.currentProgress * 0.95f + reloadProgress * 0.050000012f, 0.0f, 1.0f);
        if (f < 1.0f) {
            this.renderProgressBar(context, width / 2 - 100, scale - 5, width / 2 + 100, scale + 5, 1.0f - Mth.clamp(f, 0.0f, 1.0f));
        }
        if (f >= 2.0f) {
            this.minecraft.setOverlay(null);
        }
        if (this.fadeOutStart == -1L && this.reload.isDone() && (!this.fadeIn || g >= 2.0f)) {
            try {
                this.reload.checkExceptions();
                this.onFinish.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.onFinish.accept(Optional.of(throwable));
            }
            this.fadeOutStart = Util.getMillis();
            if (this.minecraft.screen != null) {
                this.minecraft.screen.init(this.minecraft, this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight());
            }
        }
    }

    @Unique
    private void renderProgressBar(GuiGraphics context, int minX, int minY, int maxX, int maxY, float opacity) {
        int i = Mth.ceil((float) (maxX - minX - 2) * this.currentProgress);
        int j = Math.round(opacity * 255.0f);
        int k = ARGB.color(j, 255, 255, 255);
        context.fill(minX + 2, minY + 2, minX + i, maxY - 2, k);
        context.fill(minX + 1, minY, maxX - 1, minY + 1, k);
        context.fill(minX + 1, maxY, maxX - 1, maxY - 1, k);
        context.fill(minX, minY, minX + 1, maxY, k);
        context.fill(maxX, minY, maxX - 1, maxY, k);
    }

    @Unique
    private float easeIn(float t) {
        return t * t;
    }

    @Unique
    private float easeOut(float t) {
        return flip(easeIn(flip(t)));
    }

    @Unique
    private float flip(float x) {
        return 1 - x;
    }
}
