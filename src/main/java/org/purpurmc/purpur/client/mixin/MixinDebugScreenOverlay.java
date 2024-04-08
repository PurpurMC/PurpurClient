package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.network.BeehivePacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class MixinDebugScreenOverlay {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private HitResult block;

    @Unique
    private long lastTime = 0;
    @Unique
    private BlockPos lastPos = null;

    @Redirect(method = "drawSystemInformation", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;getSystemInformation()Ljava/util/List;"))
    protected List<String> drawSystemInformation(DebugScreenOverlay instance) {
        List<String> list = this.getSystemInformation();

        if (this.minecraft.showOnlyReducedInfo() || this.minecraft.level == null || !PurpurClient.instance().getConfig().beeCountInDebug) {
            return list;
        }

        BlockPos pos = ((BlockHitResult) this.block).getBlockPos();
        BlockState state = this.minecraft.level.getBlockState(pos);

        if (state.getBlock() instanceof BeehiveBlock) {
            long now = System.currentTimeMillis();
            if (now > this.lastTime + 500 || !pos.equals(this.lastPos)) {
                this.lastPos = pos;
                this.lastTime = now;
                BeehivePacket.requestBeehiveData(pos);
            }
            if (BeehivePacket.numOfBees != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).contains("honey_level")) {
                        list.add(i + 1, "num_of_bees: " + BeehivePacket.numOfBees);
                        break;
                    }
                }
            }
        }

        return list;
    }

    @Shadow
    @SuppressWarnings("SameReturnValue")
    protected List<String> getSystemInformation() {
        return null;
    }
}
