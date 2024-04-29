package org.purpurmc.purpur.client.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ServerboundPurpurClientHelloPayload(int protocol) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ServerboundPurpurClientHelloPayload> STREAM_CODEC = CustomPacketPayload.codec(ServerboundPurpurClientHelloPayload::write, ServerboundPurpurClientHelloPayload::new);
    public static final Type<ServerboundPurpurClientHelloPayload> TYPE = new Type<>(new ResourceLocation("purpur", "client"));
    public static final int PROTOCOL = 0;

    public ServerboundPurpurClientHelloPayload(){
        this(PROTOCOL);
    }

    public ServerboundPurpurClientHelloPayload(FriendlyByteBuf friendlyByteBuf) {
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
