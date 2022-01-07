package org.purpurmc.purpur.client.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public enum Mob {
    AXOLOTL(EntityType.AXOLOTL, 0, 0),
    BAT(EntityType.BAT, 1, 0),
    BEE(EntityType.BEE, 2, 0),
    BLAZE(EntityType.BLAZE, 3, 0),
    CAT(EntityType.CAT, 4, 0),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, 5, 0),
    CHICKEN(EntityType.CHICKEN, 6, 0),
    COD(EntityType.COD, 7, 0),
    COW(EntityType.COW, 8, 0),
    CREEPER(EntityType.CREEPER, 9, 0),
    DOLPHIN(EntityType.DOLPHIN, 10, 0),
    DONKEY(EntityType.DONKEY, 11, 0),
    DROWNED(EntityType.DROWNED, 12, 0),
    ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, 13, 0),
    ENDER_DRAGON(EntityType.ENDER_DRAGON, 14, 0),
    ENDERMAN(EntityType.ENDERMAN, 15, 0),
    ENDERMITE(EntityType.ENDERMITE, 0, 2),
    EVOKER(EntityType.EVOKER, 1, 2),
    FOX(EntityType.FOX, 2, 2),
    GHAST(EntityType.GHAST, 3, 2),
    GIANT(EntityType.GIANT, 4, 2),
    GLOW_SQUID(EntityType.GLOW_SQUID, 5, 2),
    GOAT(EntityType.GOAT, 6, 2),
    GUARDIAN(EntityType.GUARDIAN, 7, 2),
    HOGLIN(EntityType.HOGLIN, 8, 2),
    HORSE(EntityType.HORSE, 9, 2),
    HUSK(EntityType.HUSK, 10, 2),
    ILLUSIONER(EntityType.ILLUSIONER, 11, 2),
    IRON_GOLEM(EntityType.IRON_GOLEM, 12, 2),
    LLAMA(EntityType.LLAMA, 13, 2),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, 14, 2),
    MOOSHROOM(EntityType.MOOSHROOM, 15, 2),
    MULE(EntityType.MULE, 0, 4),
    OCELOT(EntityType.OCELOT, 1, 4),
    PANDA(EntityType.PANDA, 2, 4),
    PARROT(EntityType.PARROT, 3, 4),
    PHANTOM(EntityType.PHANTOM, 4, 4),
    PIG(EntityType.PIG, 5, 4),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 6, 4),
    PIGLIN(EntityType.PIGLIN, 7, 4),
    PILLAGER(EntityType.PILLAGER, 8, 4),
    POLAR_BEAR(EntityType.POLAR_BEAR, 9, 4),
    PUFFERFISH(EntityType.PUFFERFISH, 10, 4),
    RABBIT(EntityType.RABBIT, 11, 4),
    RAVAGER(EntityType.RAVAGER, 12, 4),
    SALMON(EntityType.SALMON, 13, 4),
    SHEEP(EntityType.SHEEP, 14, 4),
    SHULKER(EntityType.SHULKER, 15, 4),
    SILVERFISH(EntityType.SILVERFISH, 0, 6),
    SKELETON(EntityType.SKELETON, 1, 6),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, 2, 6),
    SLIME(EntityType.SLIME, 3, 6),
    SNOW_GOLEM(EntityType.SNOW_GOLEM, 4, 6),
    SPIDER(EntityType.SPIDER, 5, 6),
    SQUID(EntityType.SQUID, 6, 6),
    STRAY(EntityType.STRAY, 7, 6),
    STRIDER(EntityType.STRIDER, 8, 6),
    TRADER_LLAMA(EntityType.TRADER_LLAMA, 9, 6),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, 10, 6),
    TURTLE(EntityType.TURTLE, 11, 6),
    VEX(EntityType.VEX, 12, 6),
    VILLAGER(EntityType.VILLAGER, 13, 6),
    VINDICATOR(EntityType.VINDICATOR, 14, 6),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, 15, 6),
    WITCH(EntityType.WITCH, 0, 8),
    WITHER(EntityType.WITHER, 1, 8),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, 2, 8),
    WOLF(EntityType.WOLF, 3, 8),
    ZOGLIN(EntityType.ZOGLIN, 4, 8),
    ZOMBIE(EntityType.ZOMBIE, 5, 8),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, 6, 8),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, 7, 8),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, 8, 8);

    private final EntityType<? extends MobEntity> mob;
    private final int u, v;

    Mob(EntityType<? extends MobEntity> mob, int u, int v) {
        this.mob = mob;
        this.u = u;
        this.v = v;
    }

    public EntityType<? extends MobEntity> getType() {
        return this.mob;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }
}
