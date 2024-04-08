package org.purpurmc.purpur.client.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class Packet {
    public static void send(ResourceLocation channel, ByteArrayDataOutput out) {
        ClientPlayNetworking.send(channel, new FriendlyByteBuf(Unpooled.wrappedBuffer(out.toByteArray())));
    }

    @SuppressWarnings("UnstableApiUsage")
    public static ByteArrayDataOutput out() {
        return ByteStreams.newDataOutput();
    }
}
