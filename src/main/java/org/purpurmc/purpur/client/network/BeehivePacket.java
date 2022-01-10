package org.purpurmc.purpur.client.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.purpurmc.purpur.client.util.Constants;

public class BeehivePacket {
    public static String numOfBees = null;

    public static void requestBeehiveData(BlockPos pos) {
        ByteArrayDataOutput out = Packet.out();
        out.writeLong(pos.asLong());
        Packet.send(Constants.BEEHIVE_C2S, out);
    }

    @SuppressWarnings("unused")
    public static void receiveBeehiveData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (client.world == null) {
            return;
        }

        ByteArrayDataInput in = Packet.in(buf.getWrittenBytes());
        int count = in.readInt();
        long packedPos = in.readLong();
        BlockPos pos = BlockPos.fromLong(packedPos);

        BlockState state = client.world.getBlockState(pos);
        if (!(state.getBlock() instanceof BeehiveBlock)) {
            return;
        }

        BeehivePacket.numOfBees = String.valueOf(count);
    }
}
