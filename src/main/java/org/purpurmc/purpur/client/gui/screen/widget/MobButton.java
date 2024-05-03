package org.purpurmc.purpur.client.gui.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.MobScreen;

public class MobButton extends SpriteIconButton {
    public static final int MOBS_ATLAS_WIDTH = 512;
    public static final int MOBS_TEXTURE_WIDTH = 16;
    public static final ResourceLocation MOBS_TEXTURE = new ResourceLocation("purpurclient", "textures/mobs.png");

    public MobButton(Minecraft client, Screen screen, Mob mob) {
        super(MOBS_TEXTURE_WIDTH, MOBS_TEXTURE_WIDTH,
            mob.getType().getDescription(),
            mob.getSpriteWidth(),
            mob.getSpriteHeight(),
            MOBS_TEXTURE,
            (button) -> client.setScreen(new MobScreen(screen, mob)), null);
        this.setTooltip(Tooltip.create(this.getMessage()));
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderWidget(context, mouseX, mouseY, delta);
        context.blit(
            MOBS_TEXTURE,
            this.getX(),
            this.getY(),
            this.spriteWidth * this.width,
            this.spriteHeight * this.height,
            this.width,
            this.height,
            this.width * MOBS_TEXTURE_WIDTH,
            this.height * MOBS_TEXTURE_WIDTH
        );
    }

    @Override
    public void renderString(GuiGraphics guiGraphics, Font font, int i) {}
}
