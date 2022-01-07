package org.purpurmc.purpur.client;

import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.ConfigManager;
import org.purpurmc.purpur.client.network.BeehivePacket;
import org.purpurmc.purpur.client.network.Packet;
import org.purpurmc.purpur.client.util.Constants;

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
            new IllegalStateException("Could not load purpur-client configuration").printStackTrace();
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
    }

    public Config getConfig() {
        return this.configManager.getConfig();
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }
}
