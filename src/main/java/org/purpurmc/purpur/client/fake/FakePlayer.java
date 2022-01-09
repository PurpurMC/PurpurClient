package org.purpurmc.purpur.client.fake;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;

public class FakePlayer extends AbstractClientPlayerEntity {
    public FakePlayer(FakeWorld world, ClientPlayerEntity player) {
        super(world, player.getGameProfile());
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
