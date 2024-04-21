package org.purpurmc.purpur.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;

public record ClientboundBeehivePacket(BlockPos pos, int numOfBees) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, ClientboundBeehivePacket> STREAM_CODEC = CustomPacketPayload.codec(ClientboundBeehivePacket::write, ClientboundBeehivePacket::new);
    public static final Type<ClientboundBeehivePacket> TYPE = new Type<>(new ResourceLocation("purpur", "beehive_s2c"));
    public static String NUM_OF_BEES = null;

    public ClientboundBeehivePacket(FriendlyByteBuf friendlyByteBuf) {
        this(friendlyByteBuf.readBlockPos(),  friendlyByteBuf.readInt());
    }

    private void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
        friendlyByteBuf.writeInt(this.numOfBees);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientboundBeehivePacket payload, ClientPlayNetworking.Context context) {
        if (context.client().level == null) {
            return;
        }

        BlockState state = context.client().level.getBlockState(payload.pos());
        if (!(state.getBlock() instanceof BeehiveBlock)) {
            return;
        }

        ClientboundBeehivePacket.NUM_OF_BEES = String.valueOf(payload.numOfBees());
    }
}
