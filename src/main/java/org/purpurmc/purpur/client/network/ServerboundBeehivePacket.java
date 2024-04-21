package org.purpurmc.purpur.client.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ServerboundBeehivePacket(BlockPos pos) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ServerboundBeehivePacket> STREAM_CODEC = CustomPacketPayload.codec(ServerboundBeehivePacket::write, ServerboundBeehivePacket::new);
    public static final Type<ServerboundBeehivePacket> TYPE = new Type<>(new ResourceLocation("purpur", "beehive_c2s"));

    public ServerboundBeehivePacket(FriendlyByteBuf friendlyByteBuf) {
        this(friendlyByteBuf.readBlockPos());
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
