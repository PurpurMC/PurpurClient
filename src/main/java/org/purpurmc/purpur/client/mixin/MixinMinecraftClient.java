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
    private ServerInfo currentServerEntry;

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
            if (this.server != null && !this.server.isRemote()) {
                sb.append(I18n.translate("purpurclient.title.singleplayer", username));
            } else if (client.isConnectedToRealms()) {
                sb.append(I18n.translate("purpurclient.title.multiplayer.realms", username));
            } else if (this.server != null || this.currentServerEntry != null && this.currentServerEntry.isLocal()) {
                sb.append(I18n.translate("purpurclient.title.multiplayer.lan", username));
            } else {
                if (this.currentServerEntry == null) {
                    sb.append(I18n.translate("purpurclient.title.multiplayer.unknown", username));
                } else {
                    sb.append(I18n.translate("purpurclient.title.multiplayer.server", username, this.currentServerEntry.name));
                }
            }
        }
        cir.setReturnValue(sb.toString());
    }
}
