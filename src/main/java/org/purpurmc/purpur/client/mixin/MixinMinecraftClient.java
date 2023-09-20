package org.purpurmc.purpur.client.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    private IntegratedServer server;
    @Shadow
    public ServerInfo getCurrentServerEntry() {
        return null;
    }

    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        PurpurClient purpur = PurpurClient.instance();
        if (purpur == null || !purpur.getConfig().useWindowTitle) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        StringBuilder sb = new StringBuilder(I18n.translate("PurpurClient %s", SharedConstants.getGameVersion().getName()));
        ClientPlayNetworkHandler network = client.getNetworkHandler();
        if (network != null && network.getConnection().isOpen()) {
            sb.append(" - ");
            String username = client.getSession().getUsername();
            ServerInfo serverInfo = this.getCurrentServerEntry();
            if (this.server != null && !this.server.isRemote()) {
                sb.append(I18n.translate("purpurclient.title.singleplayer", username));
            } else if (serverInfo != null && serverInfo.isRealm()) {
                sb.append(I18n.translate("purpurclient.title.multiplayer.realms", username));
            } else if (this.server == null && (serverInfo == null || !serverInfo.isLocal())) {
                if (serverInfo == null) {
                    sb.append(I18n.translate("purpurclient.title.multiplayer.unknown", username));
                } else {
                    sb.append(I18n.translate("purpurclient.title.multiplayer.server", username, serverInfo.name));
                }
            } else {
                sb.append(I18n.translate("purpurclient.title.multiplayer.lan", username));
            }
        }
        cir.setReturnValue(sb.toString());
    }
}
