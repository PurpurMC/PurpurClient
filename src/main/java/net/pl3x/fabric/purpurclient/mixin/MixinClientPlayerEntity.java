package net.pl3x.fabric.purpurclient.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {
    private static final String PROTOCOL_VERSION = "1";
    private static final Identifier IDENTIFIER = new Identifier("purpurclient:protocol");

    @Shadow
    private String serverBrand;
    @Shadow
    public ClientPlayNetworkHandler networkHandler;

    @Inject(at = @At("RETURN"), method = "setServerBrand")
    public void read(CallbackInfo info) {
        if (serverBrand != null && serverBrand.equals("Purpur")) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer()).writeString(PROTOCOL_VERSION);
            networkHandler.sendPacket(new CustomPayloadC2SPacket(IDENTIFIER, buf));
        }
    }
}
