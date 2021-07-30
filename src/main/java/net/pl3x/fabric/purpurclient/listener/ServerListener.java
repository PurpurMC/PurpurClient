package net.pl3x.fabric.purpurclient.listener;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.pl3x.fabric.purpurclient.PurpurClient;

public class ServerListener {
    private final PurpurClient purpur;

    public ServerListener(PurpurClient purpur) {
        this.purpur = purpur;
    }

    public void initialize() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!client.isInSingleplayer()) {
                this.purpur.getNetworkManager().tellServerWeArePurpur();
            }
        });
    }
}
