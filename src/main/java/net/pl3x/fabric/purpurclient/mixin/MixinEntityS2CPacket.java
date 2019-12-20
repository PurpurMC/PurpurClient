package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityS2CPacket.class)
public abstract class MixinEntityS2CPacket implements Packet<ClientPlayPacketListener> {
    @Shadow
    protected boolean field_20849;

    @Inject(at = @At("HEAD"), method = "read")
    public void read(CallbackInfo info) {
        this.field_20849 = true;
    }
}
