package org.purpurmc.purpur.client.fake;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.map.MapState;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.entity.EntityLookup;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.QueryableTickScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FakeWorld extends ClientWorld {
    private static final DynamicRegistryManager registryManager = DynamicRegistryManager.BUILTIN.get();

    private final Scoreboard scoreboard;

    public FakeWorld() {
        super(null, null, null, MinecraftClient.getInstance().world.getDimensionEntry(), 3, 3, MinecraftClient.getInstance()::getProfiler, MinecraftClient.getInstance().worldRenderer, false, 0);
        this.scoreboard = new Scoreboard();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isRegionLoaded(int minX, int minZ, int maxX, int maxZ) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isPosLoaded(int x, int z) {
        return true;
    }

    @Override
    public boolean isChunkLoaded(int var1, int var2) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getBrightness(BlockPos pos) {
        return 1.0F;
    }

    @Override
    public List<Entity> getOtherEntities(@Nullable Entity except, Box box, Predicate<? super Entity> predicate) {
        return Collections.emptyList();
    }

    @Override
    public <T extends Entity> List<T> getEntitiesByType(TypeFilter<Entity, T> filter, Box box, Predicate<? super T> predicate) {
        return Collections.emptyList();
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.EASY;
    }

    @Override
    public boolean isSpaceEmpty(@Nullable Entity entity, Box box) {
        return true;
    }

    @Override
    public BlockPos getSpawnPos() {
        return BlockPos.ORIGIN;
    }

    @Override
    public float getSpawnAngle() {
        return 0;
    }

    @Override
    public void setSpawnPos(BlockPos pos, float angle) {
    }

    @Override
    public void calculateAmbientDarkness() {
    }

    @Override
    protected void initWeatherGradients() {
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public long getTimeOfDay() {
        return 0;
    }

    @Override
    public void updateListeners(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
    }

    @Override
    public void playSound(@Nullable PlayerEntity except, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
    }

    @Override
    public void playSoundFromEntity(@Nullable PlayerEntity except, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
    }

    @Override
    public String asString() {
        return null;
    }

    @Nullable
    @Override
    public Entity getEntityById(int id) {
        return null;
    }

    @Nullable
    @Override
    public MapState getMapState(String id) {
        return null;
    }

    @Override
    public void putMapState(String id, MapState state) {
    }

    @Override
    public int getNextMapId() {
        return 0;
    }

    @Override
    public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public RecipeManager getRecipeManager() {
        return null;
    }

    @Override
    protected EntityLookup<Entity> getEntityLookup() {
        return null;
    }

    @Override
    public QueryableTickScheduler<Block> getBlockTickScheduler() {
        return null;
    }

    @Override
    public QueryableTickScheduler<Fluid> getFluidTickScheduler() {
        return null;
    }

    @Override
    public ClientChunkManager getChunkManager() {
        return null;
    }

    @Override
    public void syncWorldEvent(@Nullable PlayerEntity player, int eventId, BlockPos pos, int data) {
    }

    @Override
    public void emitGameEvent(@Nullable Entity entity, GameEvent event, BlockPos pos) {
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        return registryManager;
    }

    @Override
    public float getBrightness(Direction direction, boolean shaded) {
        return 0;
    }

    @Override
    public List<AbstractClientPlayerEntity> getPlayers() {
        return null;
    }

    @Override
    public RegistryEntry<Biome> getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
        return null;
    }
}
