package org.purpurmc.purpur.client.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.purpurmc.purpur.client.util.Constants;

public class BeehivePacket {
    public static String numOfBees = null;

    public static void requestBeehiveData(BlockPos pos) {
        ByteArrayDataOutput out = Packet.out();
        out.writeLong(pos.asLong());
        Packet.send(Constants.BEEHIVE_C2S, out);
    }

    @SuppressWarnings("unused")
    public static void receiveBeehiveData(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
        if (client.level == null) {
            return;
        }

        int count = buf.readInt();
        BlockPos pos = buf.readBlockPos();

        BlockState state = client.level.getBlockState(pos);
        if (!(state.getBlock() instanceof BeehiveBlock)) {
            return;
        }

        BeehivePacket.numOfBees = String.valueOf(count);
    }
}
