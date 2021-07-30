package net.pl3x.fabric.purpurclient.network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.fabric.purpurclient.util.Constants;

public class NetworkManager {
    private final Identifier channel = new Identifier("purpur", "client");

    public void tellServerWeArePurpur() {
        ByteArrayDataOutput out = out();
        out.writeInt(Constants.HELLO);
        send(out);
    }

    private void send(ByteArrayDataOutput out) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            ClientPlayNetworking.send(this.channel, new PacketByteBuf(Unpooled.wrappedBuffer(out.toByteArray())));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private ByteArrayDataOutput out() {
        return ByteStreams.newDataOutput();
    }
}
