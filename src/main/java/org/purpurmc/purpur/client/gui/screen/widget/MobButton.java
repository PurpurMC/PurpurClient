package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.AbstractScreen;
import org.purpurmc.purpur.client.gui.screen.MobScreen;

public class MobButton extends ButtonWidget {
    public static final Identifier MOBS_TEXTURE = new Identifier("purpurclient", "textures/mobs.png");

    private final AbstractScreen screen;
    private final Mob mob;

    public MobButton(AbstractScreen screen, Mob mob, int x, int y) {
        super(x, y, 16, 16, mob.getType().getName(), (button) -> screen.openScreen(new MobScreen(screen, mob)), DEFAULT_NARRATION_SUPPLIER);
        this.screen = screen;
        this.mob = mob;
    }

    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, MOBS_TEXTURE);
        RenderSystem.enableDepthTest();
        context.drawTexture(
            MOBS_TEXTURE,
            this.getX(),
            this.getY(),
            this.width * 15,
            this.height * 14 + (this.isSelected() ? this.height : 0),
            this.width,
            this.height,
            this.width * 16,
            this.height * 16
        );
        context.drawTexture(
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
        if (this.hovered) {
            this.renderTooltip(context, mouseX, mouseY);
        }
    }

    public void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        context.drawTooltip(MinecraftClient.getInstance().textRenderer, this.getMessage(), mouseX, mouseY);
    }
}
