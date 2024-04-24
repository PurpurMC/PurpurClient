package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.MobScreen;

public class MobButton extends SpriteIconButton {
    public static final ResourceLocation MOBS_TEXTURE = new ResourceLocation("purpurclient", "textures/mobs.png");

    public MobButton(Minecraft client, Screen screen, Mob mob) {
        super(16, 16,
            mob.getType().getDescription(),
            mob.getSpriteWidth(),
            mob.getSpriteHeight(),
            MOBS_TEXTURE,
            (button) -> client.setScreen(new MobScreen(screen, mob)), null);
    }

//    @Override
//    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
//        super.renderWidget(guiGraphics, i, j, f);
//        int k = this.getX() + this.getWidth() / 2 - this.spriteWidth / 2;
//        int l = this.getY() + this.getHeight() / 2 - this.spriteHeight / 2;
//        guiGraphics.blitSprite(this.sprite, k, l, this.spriteWidth, this.spriteHeight);
//    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        //TODO: replace with guiGraphics.blitSprite()
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MOBS_TEXTURE);
        RenderSystem.enableDepthTest();
        context.blit(
            MOBS_TEXTURE,
            this.getX(),
            this.getY(),
            this.width * 15,
            this.height * 14 + (this.isHoveredOrFocused() ? this.height : 0),
            this.width,
            this.height,
            this.width * 16,
            this.height * 16
        );
        context.blit(
            MOBS_TEXTURE,
            this.getX(),
            this.getY(),
            this.spriteWidth * this.width,
            this.spriteHeight * this.height,
            this.width,
            this.height,
            this.width * 16,
            this.height * 16
        );
        if (this.isHovered) {
            this.renderTooltip(context, mouseX, mouseY);
        }
    }

    public void renderTooltip(GuiGraphics context, int mouseX, int mouseY) {
        context.renderTooltip(Minecraft.getInstance().font, this.getMessage(), mouseX, mouseY);
    }
}
