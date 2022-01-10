package org.purpurmc.purpur.client.mixin;


import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.chat.Chat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Final
    @Shadow
    private MinecraftClient client;
    @Final
    @Shadow
    private Map<MessageType, List<ClientChatListener>> listeners = Maps.newHashMap();

    private final LinkedBlockingQueue<Chat> queue = new LinkedBlockingQueue<>();
    private boolean running;

    @Inject(method = "addChatMessage", at = @At("HEAD"), cancellable = true)
    private void injectAddChatMessage(MessageType type, Text message, UUID sender, CallbackInfo ci) {
        if (PurpurClient.instance().getConfig().fixChatStutter) {
            // queue up the chat
            this.queue.add(new Chat(type, message, sender));
            if (!this.running) {
                runAsyncInfiniteLoop();
            }
            // cancel original method
            ci.cancel();
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

    private void runAsyncInfiniteLoop() {
        this.running = true;
        new Thread(() -> {
            while (this.running) {
                try {
                    Chat chat = this.queue.take();
                    addChatMessage0(chat.type(), chat.message(), chat.sender());
                } catch (InterruptedException e) {
                    this.running = false;
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
