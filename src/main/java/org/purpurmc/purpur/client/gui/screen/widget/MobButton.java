package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.AbstractScreen;
import org.purpurmc.purpur.client.gui.screen.MobScreen;

public class MobButton extends Button {
    public static final ResourceLocation MOBS_TEXTURE = new ResourceLocation("purpurclient", "textures/mobs.png");

    private final AbstractScreen screen;
    private final Mob mob;

    public MobButton(AbstractScreen screen, Mob mob, int x, int y) {
        super(x, y, 16, 16, mob.getType().getDescription(), (button) -> screen.openScreen(new MobScreen(screen, mob)), DEFAULT_NARRATION);
        this.screen = screen;
        this.mob = mob;
    }

    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
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
            this.mob.getU() * this.width,
            this.mob.getV() * this.height,
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
