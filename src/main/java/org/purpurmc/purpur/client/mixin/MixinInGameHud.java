package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
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
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    private final LinkedBlockingQueue<Chat> queue = new LinkedBlockingQueue<>();
    @Final
    @Shadow
    private MinecraftClient client;
    @Final
    @Shadow
    private List<ClientChatListener> listeners;
    private boolean running;

    @Shadow
    @SuppressWarnings({"SameReturnValue", "unused"})
    public UUID extractSender(Text message) {
        return null;
    }

    @Inject(method = "onChatMessage", at = @At("HEAD"), cancellable = true)
    private void injectOnChatMessage(MessageType type, Text message, MessageSender sender, CallbackInfo ci) {
        if (PurpurClient.instance().getConfig().fixChatStutter) {
            // queue up the chat
            this.queue.add(new Chat(type, message, sender));
            // start loop if it's not running
            if (!this.running) {
                runAsyncInfiniteLoop();
            }
            // cancel original method
            ci.cancel();
        }
    }

    public void onChatMessage0(MessageType type, Text message, MessageSender sender) {
        if (!this.client.shouldBlockMessages(sender.uuid())) {
            if (!(Boolean) this.client.options.getHideMatchedNames().getValue() || !this.client.shouldBlockMessages(this.extractSender(message))) {

                for (ClientChatListener clientChatListener : this.listeners) {
                    clientChatListener.onChatMessage(type, message, sender);
                }

            }
        }
    }

    private void runAsyncInfiniteLoop() {
        this.running = true;
        new Thread(() -> {
            while (this.running) {
                try {
                    Chat chat = this.queue.take();
                    onChatMessage0(chat.type(), chat.message(), chat.sender());
                } catch (InterruptedException e) {
                    this.running = false;
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
