package org.purpurmc.purpur.client.chat;

import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

import java.util.UUID;

public record Chat(MessageType type, Text message, UUID sender) {
}
