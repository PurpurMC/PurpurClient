package org.purpurmc.purpur.client.mixin;


import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Final
    @Shadow
    private MinecraftClient client;
    @Final
    @Shadow
    private Map<MessageType, List<ClientChatListener>> listeners = Maps.newHashMap();

    /**
     * @author BillyGalbreath
     * @reason because bacon.
     */
    @Overwrite
    public void addChatMessage(MessageType type, Text message, UUID sender) {
        if (PurpurClient.instance().getConfig().fixChatStutter) {
            // let's hail mary it and move this whole thing to another thread
            CompletableFuture.runAsync(() -> addChatMessage0(type, message, sender));
        } else {
            addChatMessage0(type, message, sender);
        }
    }

    private void addChatMessage0(MessageType type, Text message, UUID sender) {
        if (this.client.shouldBlockMessages(sender)) {
            return;
        }
        if (this.client.options.hideMatchedNames && this.client.shouldBlockMessages(this.extractSender(message))) {
            return;
        }

        for (ClientChatListener clientChatListener : this.listeners.get(type)) {
            clientChatListener.onChatMessage(type, message, sender);
        }
    }

    @Shadow
    @SuppressWarnings({"SameReturnValue", "unused"})
    public UUID extractSender(Text message) {
        return null;
    }
}
