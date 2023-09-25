package org.purpurmc.purpur.client.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class Packet {
    public static void send(Identifier channel, ByteArrayDataOutput out) {
        ClientPlayNetworking.send(channel, new PacketByteBuf(Unpooled.wrappedBuffer(out.toByteArray())));
    }

    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataOutput out() {
        return ByteStreams.newDataOutput();
    }
}
