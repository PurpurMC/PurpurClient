package org.purpurmc.purpur.client.gui;

import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.purpurmc.purpur.client.PurpurClient;

import java.io.IOException;
import java.io.InputStream;

public class SplashTexture extends ResourceTexture {
    public static final Identifier SPLASH = new Identifier("purpurclient", "textures/splash.png");

    public SplashTexture() {
        super(SPLASH);
    }

    @Override
    protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
        ResourceTexture.TextureData data;
        try (InputStream in = PurpurClient.class.getResourceAsStream("/assets/purpurclient/textures/splash.png")) {
            data = new TextureData(new TextureResourceMetadata(true, true), NativeImage.read(in));
        } catch (IOException e) {
            return new TextureData(e);
        }
        return data;
    }
}
