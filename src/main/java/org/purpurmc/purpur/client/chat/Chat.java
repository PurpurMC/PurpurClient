package org.purpurmc.purpur.client.chat;

import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;

public record Chat(MessageType type, Text message, MessageSender sender) {
}
