package org.purpurmc.purpur.client.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.purpurmc.purpur.client.network.BeehivePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Shadow
    @Final
    private MinecraftClient client;

    private long lastTime = 0;
    private BlockPos lastPos = null;

    @Inject(method = "render", at = @At("TAIL"))
    private void render(MatrixStack stack, CallbackInfo ci) {
        if (this.client.world == null) {
            return;
        }

        Entity entity = this.client.getCameraEntity();
        if (entity == null) {
            return;
        }

        HitResult hit = entity.raycast(20, 0, false);
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();

        BlockState state = this.client.world.getBlockState(pos);
        if (!state.contains(BeehivePacket.NUM_OF_BEES)) {
            return;
        }

        long now = System.currentTimeMillis();
        if (now > lastTime + 500 || !pos.equals(lastPos)) {
            lastPos = pos;
            lastTime = now;

            BeehivePacket packet = new BeehivePacket();
            packet.requestBeehiveData(pos);
        }
    }
}
