package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.EntityS2CPacket;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler implements ClientPlayPacketListener {
    @Shadow private ClientWorld world;

    public void onEntityUpdate(final EntityS2CPacket packet) {
        final Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            entity.trackedX += packet.getDeltaXShort();
            entity.trackedY += packet.getDeltaYShort();
            entity.trackedZ += packet.getDeltaZShort();
            Vec3d vec3d = EntityS2CPacket.decodePacketCoordinates(entity.trackedX, entity.trackedY, entity.trackedZ);
            if (!entity.isLogicalSideForUpdatingMovement()) {
                float yaw = packet.hasRotation() ? (packet.getYaw() * 360 / 256.0f) : entity.yaw;
                float pitch = packet.hasRotation() ? (packet.getPitch() * 360 / 256.0f) : entity.pitch;
                entity.updateTrackedPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, yaw, pitch, 3, false);
                entity.onGround = packet.isOnGround();
            }
        }
    }
}
