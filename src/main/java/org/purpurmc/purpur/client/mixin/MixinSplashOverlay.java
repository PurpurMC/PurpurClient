package org.purpurmc.purpur.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.gui.SplashTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    @Final
    private boolean reloading;
    @Shadow
    @Final
    private ResourceReload reload;
    @Shadow
    private float progress;
    @Shadow
    private long reloadCompleteTime;
    @Shadow
    private long reloadStartTime;
    @Shadow
    @Final
    private Consumer<Optional<Throwable>> exceptionHandler;

    @Inject(method = "init", at = @At("HEAD"))
    private static void init(MinecraftClient client, CallbackInfo ci) {
        client.getTextureManager().registerTexture(SplashTexture.SPLASH, new SplashTexture());
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!PurpurClient.instance().getConfig().usePurpurSplash) {
            return;
        }

        ci.cancel();

        int width = this.client.getWindow().getScaledWidth();
        int height = this.client.getWindow().getScaledHeight();

        long curTime = Util.getMeasuringTimeMs();
        if (this.reloading && this.reloadStartTime == -1L) {
            this.reloadStartTime = curTime;
        }
        float f = this.reloadCompleteTime > -1L ? (float) (curTime - this.reloadCompleteTime) / 1000.0f : -1.0f;
        float g = this.reloadStartTime > -1L ? (float) (curTime - this.reloadStartTime) / 500.0f : -1.0f;
        float opacity;

        if (f >= 1.0f) {
            if (this.client.currentScreen != null) {
                this.client.currentScreen.render(matrixStack, 0, 0, delta);
            }
            opacity = 1.0f - MathHelper.clamp(f - 1.0f, 0.0f, 1.0f);
        } else if (this.reloading) {
            if (this.client.currentScreen != null && g < 1.0f) {
                this.client.currentScreen.render(matrixStack, mouseX, mouseY, delta);
            }
            opacity = MathHelper.clamp(g, 0.0f, 1.0f);
        } else {
            opacity = 1.0f;
        }

        RenderSystem.setShaderTexture(0, SplashTexture.SPLASH);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        SplashOverlay.drawTexture(matrixStack, 0, 0, width, height, 0, 0, 1024, 544, 1024, 1024); // background
        SplashOverlay.drawTexture(matrixStack, width - 112, 10, 112, 112, 0, 546, 256, 256, 1024, 1024); // logo
        SplashOverlay.drawTexture(matrixStack, 20, height - 70, 180, 17, 256, 548, 367, 33, 1024, 1024); // slogan
        SplashOverlay.drawTexture(matrixStack, 20, height - 50, 100, 30, 256, 587, 210, 61, 1024, 1024); // Purpur
        SplashOverlay.drawTexture(matrixStack, width - 105, height - 15, 100, 12, 256, 658, 200, 23, 1024, 1024); // url
        RenderSystem.disableBlend();

        int scale = (int) ((double) this.client.getWindow().getScaledHeight() * 0.625D);
        float reloadProgress = this.reload.getProgress();
        this.progress = MathHelper.clamp(this.progress * 0.95f + reloadProgress * 0.050000012f, 0.0f, 1.0f);
        if (f < 1.0f) {
            this.renderProgressBar(matrixStack, width / 2 - 100, scale - 5, width / 2 + 100, scale + 5, 1.0f - MathHelper.clamp(f, 0.0f, 1.0f));
        }
        if (f >= 2.0f) {
            this.client.setOverlay(null);
        }
        if (this.reloadCompleteTime == -1L && this.reload.isComplete() && (!this.reloading || g >= 2.0f)) {
            try {
                this.reload.throwException();
                this.exceptionHandler.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.exceptionHandler.accept(Optional.of(throwable));
            }
            this.reloadCompleteTime = Util.getMeasuringTimeMs();
            if (this.client.currentScreen != null) {
                this.client.currentScreen.init(this.client, this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
            }
        }
    }

    private void renderProgressBar(MatrixStack matrices, int minX, int minY, int maxX, int maxY, float opacity) {
        int i = MathHelper.ceil((float) (maxX - minX - 2) * this.progress);
        int j = Math.round(opacity * 255.0f);
        int k = ColorHelper.Argb.getArgb(j, 255, 255, 255);
        SplashOverlay.fill(matrices, minX + 2, minY + 2, minX + i, maxY - 2, k);
        SplashOverlay.fill(matrices, minX + 1, minY, maxX - 1, minY + 1, k);
        SplashOverlay.fill(matrices, minX + 1, maxY, maxX - 1, maxY - 1, k);
        SplashOverlay.fill(matrices, minX, minY, minX + 1, maxY, k);
        SplashOverlay.fill(matrices, maxX, minY, maxX - 1, maxY, k);
    }
}
