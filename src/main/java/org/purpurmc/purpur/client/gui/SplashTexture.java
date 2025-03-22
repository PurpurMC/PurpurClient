package org.purpurmc.purpur.client.gui;

import org.purpurmc.purpur.client.PurpurClient;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class SplashTexture extends SimpleTexture {
    public static final ResourceLocation SPLASH = ResourceLocation.fromNamespaceAndPath("purpurclient", "textures/splash.png");

    public SplashTexture() {
        super(SPLASH);
    }

    @Override
    public TextureContents loadContents(ResourceManager resourceManager) throws IOException {
        try (InputStream inputStream = PurpurClient.class.getResourceAsStream("/assets/purpurclient/textures/splash.png")) {
            return new TextureContents(NativeImage.read(inputStream), new TextureMetadataSection(true, true));
        }
    }
}
