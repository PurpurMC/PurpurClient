package org.purpurmc.purpur.client.gui;

import java.io.FileNotFoundException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.purpurmc.purpur.client.PurpurClient;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;


public class SplashTexture extends SimpleTexture {
    public static final ResourceLocation SPLASH = ResourceLocation.fromNamespaceAndPath("purpurclient", "textures/splash.png");

    public SplashTexture() {
        super(SPLASH);
    }

    @Override
    public @NotNull TextureContents loadContents(ResourceManager resourceManager) {
        TextureContents data;
        try (InputStream in = PurpurClient.class.getResourceAsStream("/assets/purpurclient/textures/splash.png")) {
            data = new TextureContents(NativeImage.read(in), new TextureMetadataSection(true, true));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load splash texture", e);
        }
        return data;
    }
}
