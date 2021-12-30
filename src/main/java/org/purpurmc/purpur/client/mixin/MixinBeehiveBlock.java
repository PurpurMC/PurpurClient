package org.purpurmc.purpur.client.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;
import org.purpurmc.purpur.client.network.BeehivePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlock.class)
public abstract class MixinBeehiveBlock extends BlockWithEntity {
    public MixinBeehiveBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(BeehiveBlock.HONEY_LEVEL, 0)
                .with(BeehiveBlock.FACING, Direction.NORTH)
                .with(BeehivePacket.NUM_OF_BEES, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BeehiveBlock.HONEY_LEVEL, BeehiveBlock.FACING, BeehivePacket.NUM_OF_BEES);
    }
}
