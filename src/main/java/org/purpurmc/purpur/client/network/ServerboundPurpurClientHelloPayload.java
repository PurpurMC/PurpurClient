package org.purpurmc.purpur.client.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ServerboundPurpurClientHelloPayload(int protocol) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ServerboundPurpurClientHelloPayload> STREAM_CODEC = CustomPacketPayload.codec(ServerboundPurpurClientHelloPayload::write, ServerboundPurpurClientHelloPayload::new);
    public static final Type<ServerboundPurpurClientHelloPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("purpur", "client"));
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
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
