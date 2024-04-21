package org.purpurmc.purpur.client.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ServerboundPurpurClientHelloPacket(int protocol) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ServerboundPurpurClientHelloPacket> STREAM_CODEC = CustomPacketPayload.codec(ServerboundPurpurClientHelloPacket::write, ServerboundPurpurClientHelloPacket::new);
    public static final Type<ServerboundPurpurClientHelloPacket> TYPE = new Type<>(new ResourceLocation("purpur", "client"));
    public static final int PROTOCOL = 0;

    public ServerboundPurpurClientHelloPacket(){
        this(PROTOCOL);
    }

    public ServerboundPurpurClientHelloPacket(FriendlyByteBuf friendlyByteBuf) {
        this(friendlyByteBuf.readInt());
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(PROTOCOL);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
