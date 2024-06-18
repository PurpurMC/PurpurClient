package org.purpurmc.purpur.client.gui;

import org.purpurmc.purpur.client.PurpurClient;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class SplashTexture extends SimpleTexture {
    public static final ResourceLocation SPLASH = ResourceLocation.fromNamespaceAndPath("purpurclient", "textures/splash.png");

    public SplashTexture() {
        super(SPLASH);
    }

    @Override
    protected TextureImage getTextureImage(ResourceManager resourceManager) {
        TextureImage data;
        try (InputStream in = PurpurClient.class.getResourceAsStream("/assets/purpurclient/textures/splash.png")) {
            data = new TextureImage(new TextureMetadataSection(true, true), NativeImage.read(in));
        } catch (IOException e) {
            return new TextureImage(e);
        }
        return data;
    }
}
