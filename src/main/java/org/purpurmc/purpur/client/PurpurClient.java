package org.purpurmc.purpur.client;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.ConfigManager;
import org.purpurmc.purpur.client.network.ClientboundBeehivePayload;
import org.purpurmc.purpur.client.network.ServerboundBeehivePayload;
import org.purpurmc.purpur.client.network.ServerboundPurpurClientHelloPayload;

public class PurpurClient implements ClientModInitializer {
    private static PurpurClient instance;

    public static PurpurClient instance() {
        return instance;
    }

    public static List<IoSupplier<InputStream>> ICON_LIST = Arrays.asList(() -> PurpurClient.class.getResourceAsStream("/assets/icon16.png"), () -> PurpurClient.class.getResourceAsStream("/assets/icon32.png"));

    private final ConfigManager configManager;

    public PurpurClient() {
        instance = this;

        this.configManager = new ConfigManager();
    }

    @Override
    public void onInitializeClient() {
        if (this.configManager.getConfig() == null) {
            new IllegalStateException("Could not load purpurclient configuration").printStackTrace();
            return;
        }

        PayloadTypeRegistry.configurationC2S().register(ServerboundPurpurClientHelloPayload.TYPE, ServerboundPurpurClientHelloPayload.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ServerboundBeehivePayload.TYPE, ServerboundBeehivePayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(ClientboundBeehivePayload.TYPE, ClientboundBeehivePayload.STREAM_CODEC);

        ClientPlayNetworking.registerGlobalReceiver(ClientboundBeehivePayload.TYPE, ClientboundBeehivePayload::handle);
        ClientConfigurationConnectionEvents.READY.register((handler, client) -> {
            ClientboundBeehivePayload.NUM_OF_BEES = null;
            ClientConfigurationNetworking.send(new ServerboundPurpurClientHelloPayload());
        });

        if (getConfig().useWindowTitle) {
            Minecraft.getInstance().execute(this::updateTitle);
        }
    }

    public Config getConfig() {
        return this.configManager.getConfig();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public void updateTitle() {
        Minecraft client = Minecraft.getInstance();
        Window window = client.getWindow();
        client.updateTitle();
        VanillaPackResources pack = client.getVanillaPackResources();
        try {
            window.setIcon(pack, IconSet.RELEASE);
        } catch (IOException e) {
            // ignore
        };
    }
}
