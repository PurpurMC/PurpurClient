package org.purpurmc.purpur.client.entity;

import net.minecraft.world.entity.EntityType;

public enum Mob {
    ALLAY(EntityType.ALLAY, 9, 4),
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
    ENDERMITE(EntityType.ENDERMITE, 0, 1),
    EVOKER(EntityType.EVOKER, 1, 1),
    FOX(EntityType.FOX, 2, 1),
    FROG(EntityType.FROG, 10, 4),
    GHAST(EntityType.GHAST, 3, 1),
    GIANT(EntityType.GIANT, 4, 1),
    GLOW_SQUID(EntityType.GLOW_SQUID, 5, 1),
    GOAT(EntityType.GOAT, 6, 1),
    GUARDIAN(EntityType.GUARDIAN, 7, 1),
    HOGLIN(EntityType.HOGLIN, 8, 1),
    HORSE(EntityType.HORSE, 9, 1),
    HUSK(EntityType.HUSK, 10, 1),
    ILLUSIONER(EntityType.ILLUSIONER, 11, 1),
    IRON_GOLEM(EntityType.IRON_GOLEM, 12, 1),
    LLAMA(EntityType.LLAMA, 13, 1),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, 14, 1),
    MOOSHROOM(EntityType.MOOSHROOM, 15, 1),
    MULE(EntityType.MULE, 0, 2),
    OCELOT(EntityType.OCELOT, 1, 2),
    PANDA(EntityType.PANDA, 2, 2),
    PARROT(EntityType.PARROT, 3, 2),
    PHANTOM(EntityType.PHANTOM, 4, 2),
    PIG(EntityType.PIG, 5, 2),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 6, 2),
    PIGLIN(EntityType.PIGLIN, 7, 2),
    PILLAGER(EntityType.PILLAGER, 8, 2),
    POLAR_BEAR(EntityType.POLAR_BEAR, 9, 2),
    PUFFERFISH(EntityType.PUFFERFISH, 10, 2),
    RABBIT(EntityType.RABBIT, 11, 2),
    RAVAGER(EntityType.RAVAGER, 12, 2),
    SALMON(EntityType.SALMON, 13, 2),
    SHEEP(EntityType.SHEEP, 14, 2),
    SHULKER(EntityType.SHULKER, 15, 2),
    SILVERFISH(EntityType.SILVERFISH, 0, 3),
    SKELETON(EntityType.SKELETON, 1, 3),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, 2, 3),
    SLIME(EntityType.SLIME, 3, 3),
    SNOW_GOLEM(EntityType.SNOW_GOLEM, 4, 3),
    SPIDER(EntityType.SPIDER, 5, 3),
    SQUID(EntityType.SQUID, 6, 3),
    STRAY(EntityType.STRAY, 7, 3),
    STRIDER(EntityType.STRIDER, 8, 3),
    TADPOLE(EntityType.TADPOLE, 11, 4),
    TRADER_LLAMA(EntityType.TRADER_LLAMA, 9, 3),
    TROPICAL_FISH(EntityType.TROPICAL_FISH, 10, 3),
    TURTLE(EntityType.TURTLE, 11, 3),
    VEX(EntityType.VEX, 12, 3),
    VILLAGER(EntityType.VILLAGER, 13, 3),
    VINDICATOR(EntityType.VINDICATOR, 14, 3),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, 15, 3),
    WARDEN(EntityType.WARDEN, 12, 4),
    WITCH(EntityType.WITCH, 0, 4),
    WITHER(EntityType.WITHER, 1, 4),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, 2, 4),
    WOLF(EntityType.WOLF, 3, 4),
    ZOGLIN(EntityType.ZOGLIN, 4, 4),
    ZOMBIE(EntityType.ZOMBIE, 5, 4),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, 6, 4),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, 7, 4),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, 8, 4);

    private final EntityType<? extends net.minecraft.world.entity.Mob> mob;
    private final int u, v;

    Mob(EntityType<? extends net.minecraft.world.entity.Mob> mob, int u, int v) {
        this.mob = mob;
        this.u = u;
        this.v = v;
    }

    public EntityType<? extends net.minecraft.world.entity.Mob> getType() {
        return this.mob;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }
}
