package org.purpurmc.purpur.client.fake;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;

public class FakePlayer extends AbstractClientPlayer {
    public FakePlayer(ClientLevel world, LocalPlayer player) {
        super(world, player.getGameProfile());
    }

    @Override
    public boolean shouldShowName() {
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
