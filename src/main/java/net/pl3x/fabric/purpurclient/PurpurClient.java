package net.pl3x.fabric.purpurclient;

import net.fabricmc.api.ModInitializer;
import net.pl3x.fabric.purpurclient.listener.ServerListener;
import net.pl3x.fabric.purpurclient.network.NetworkManager;

public class PurpurClient implements ModInitializer {
    private final NetworkManager networkManager;
    private final ServerListener serverListener;

    public PurpurClient() {
        this.networkManager = new NetworkManager();
        this.serverListener = new ServerListener(this);
    }

    @Override
    public void onInitialize() {
        this.serverListener.initialize();
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }
}
