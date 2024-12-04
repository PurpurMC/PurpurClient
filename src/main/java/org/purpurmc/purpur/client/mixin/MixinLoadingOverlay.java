package org.purpurmc.purpur.client.mixin;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.util.TriState;
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
public abstract class MixinLoadingOverlay {
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

    @Unique
    private RenderType.CompositeRenderType PURPUR_LOGO = RenderType.create(
        "purpur_splash",
        DefaultVertexFormat.POSITION_TEX_COLOR,
        VertexFormat.Mode.QUADS,
        RenderType.SMALL_BUFFER_SIZE,
        RenderType.CompositeState.builder()
            .setTextureState(new RenderStateShard.TextureStateShard(SplashTexture.SPLASH, TriState.DEFAULT, false))
            .setShaderState(RenderStateShard.POSITION_TEXTURE_COLOR_SHADER)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .createCompositeState(false)
    );

    @Inject(method = "registerTextures", at = @At("HEAD"))
    private static void registerTextures(TextureManager textureManager, CallbackInfo ci) {
        textureManager.registerAndLoad(SplashTexture.SPLASH, new SplashTexture());
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

        Function<ResourceLocation, RenderType> guiTextured = resourceLocation -> PURPUR_LOGO;
        context.blit(guiTextured, SplashTexture.SPLASH, 0, 0, 0, 0, width, height, 1024, 544, 1024, 1024); // background
        context.blit(guiTextured, SplashTexture.SPLASH, (int) ((width - 112) * Mth.lerp(easeOut(opacity), 0.8D, 1.0D)), 10, 0, 546, 112, 112, 256, 256, 1024, 1024); // logo
        context.blit(guiTextured, SplashTexture.SPLASH, 40 + (int) ((20) * -Mth.lerp(easeOut(opacity), 0.0D, 1.0D)), height - 70, 256, 548, 180, 17, 367, 33, 1024, 1024); // slogan
        context.blit(guiTextured, SplashTexture.SPLASH, (int) ((20) * Mth.lerp(easeOut(opacity), -1.0D, 1.0D)), height - 50, 256, 587, 100, 30, 210, 61, 1024, 1024); // Purpur
        context.blit(guiTextured, SplashTexture.SPLASH, width - 105, (int) ((height - 15) * Mth.lerp(easeOut(opacity), 0.75D, 1.0D)), 256, 658, 100, 12, 200, 23, 1024, 1024); // url

        int scale = (int) ((double) this.minecraft.getWindow().getGuiScaledHeight() * 0.625D);
        float reloadProgress = this.reload.getActualProgress();
        this.currentProgress = Mth.clamp(this.currentProgress * 0.95f + reloadProgress * 0.050000012f, 0.0f, 1.0f);
        if (f < 1.0f) {
            this.drawProgressBar(context, width / 2 - 100, scale - 5, width / 2 + 100, scale + 5, 1.0f - Mth.clamp(f, 0.0f, 1.0f));
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
                this.minecraft.screen.init(this.minecraft, context.guiWidth(), context.guiHeight());
            }
        }
    }

    @Shadow
    abstract void drawProgressBar(GuiGraphics guiGraphics, int minX, int minY, int maxX, int maxY, float delta);

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
