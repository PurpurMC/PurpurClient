package org.purpurmc.purpur.client;

import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.ConfigManager;
import org.purpurmc.purpur.client.network.BeehivePacket;
import org.purpurmc.purpur.client.network.Packet;
import org.purpurmc.purpur.client.util.Constants;

import java.io.IOException;
import java.io.InputStream;

public class PurpurClient implements ClientModInitializer {
    private static PurpurClient instance;

    public static PurpurClient instance() {
        return instance;
    }

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

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            BeehivePacket.numOfBees = null;
            if (!client.isInSingleplayer()) {
                ByteArrayDataOutput out = Packet.out();
                out.writeInt(Constants.PROTOCOL);
                Packet.send(Constants.HELLO, out);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(Constants.BEEHIVE_S2C, BeehivePacket::receiveBeehiveData);

        if (getConfig().useWindowTitle) {
            MinecraftClient.getInstance().execute(this::updateTitle);
        }
    }

    public Config getConfig() {
        return this.configManager.getConfig();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public void updateTitle() {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.getWindow();
        client.updateWindowTitle();
        if (getConfig().useWindowTitle) {
            InputStream icon16 = PurpurClient.class.getResourceAsStream("/assets/icon16.png");
            InputStream icon32 = PurpurClient.class.getResourceAsStream("/assets/icon32.png");
            window.setIcon(icon16, icon32);
        } else {
            try {
                DefaultResourcePack pack = client.getResourcePackProvider().getPack();
                window.setIcon(
                    pack.open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_16x16.png")),
                    pack.open(ResourceType.CLIENT_RESOURCES, new Identifier("icons/icon_32x32.png")));
            } catch (IOException ignore) {
            }
        }
    }
}
