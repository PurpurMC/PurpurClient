package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.ParticlesOption;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer implements AutoCloseable, SynchronousResourceReloadListener {
    @Shadow
    private MinecraftClient client;
    @Shadow
    private int ticks;
    @Shadow
    private int field_20793;

    @Overwrite
    public void method_22713(Camera camera) {
        float f = this.client.world.getRainGradient(1.0F);
        if (!this.client.options.fancyGraphics) {
            f /= 2.0F;
        }

        if (f != 0.0F) {
            Random random = new Random((long)this.ticks * 312987231L);
            WorldView worldView = this.client.world;
            BlockPos blockPos = new BlockPos(camera.getPos());
            double d = 0.0D;
            double e = 0.0D;
            double g = 0.0D;
            int j = 0;
            int k = (int)(100.0F * f * f);
            if (this.client.options.particles == ParticlesOption.DECREASED) {
                k >>= 1;
            } else if (this.client.options.particles == ParticlesOption.MINIMAL) {
                k = 0;
            }

            for(int l = 0; l < k; ++l) {
                BlockPos blockPos2 = worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos.add(random.nextInt(10) - random.nextInt(10), 0, random.nextInt(10) - random.nextInt(10)));
                Biome biome = worldView.getBiome(blockPos2);
                BlockPos blockPos3 = blockPos2.down();
                if (blockPos2.getY() <= blockPos.getY() + 10 && blockPos2.getY() >= blockPos.getY() - 10 && biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.getTemperature(blockPos2) >= 0.15F) {
                    double h = random.nextDouble();
                    double m = random.nextDouble();
                    BlockState blockState = worldView.getBlockState(blockPos3);
                    FluidState fluidState = worldView.getFluidState(blockPos3);
                    VoxelShape voxelShape = blockState.getCollisionShape(worldView, blockPos3);
                    double n = voxelShape.getEndingCoord(Direction.Axis.Y, h, m);
                    double o = (double)fluidState.getHeight(worldView, blockPos3);
                    double r;
                    double s;
                    if (n >= o) {
                        r = n;
                        s = voxelShape.getBeginningCoord(Direction.Axis.Y, h, m);
                    } else {
                        r = o;
                        s = o;
                    }

                    if (r > -1.7976931348623157E308D) {
                        if (!fluidState.matches(FluidTags.LAVA) && blockState.getBlock() != Blocks.MAGMA_BLOCK && (blockState.getBlock() != Blocks.CAMPFIRE || !(Boolean)blockState.get(CampfireBlock.LIT))) {
                            ++j;
                            if (random.nextInt(j) == 0) {
                                d = (double)blockPos3.getX() + h;
                                e = (double)((float)blockPos3.getY() + 0.1F) + r - 1.0D;
                                g = (double)blockPos3.getZ() + m;
                            }

                            this.client.world.addParticle(ParticleTypes.RAIN, (double)blockPos3.getX() + h, (double)((float)blockPos3.getY() + 0.1F) + r, (double)blockPos3.getZ() + m, 0.0D, 0.0D, 0.0D);
                        } else {
                            this.client.world.addParticle(ParticleTypes.SMOKE, (double)blockPos2.getX() + h, (double)((float)blockPos2.getY() + 0.1F) - s, (double)blockPos2.getZ() + m, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            if (j > 0 && random.nextInt(3) < this.field_20793++) {
                this.field_20793 = 0;
                if (e > (double)(blockPos.getY() + 1) && worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > MathHelper.floor((float)blockPos.getY())) {
                    this.client.world.playSound(d, e, g, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
                } else {
                    this.client.world.playSound(d, e, g, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
                }
            }

        }
    }
}
