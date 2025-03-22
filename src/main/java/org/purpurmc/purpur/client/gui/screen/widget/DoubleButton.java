package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.purpurmc.purpur.client.config.options.DoubleOption;

public class DoubleButton extends AbstractWidget implements Tickable {
    private final static Component PLUS = Component.nullToEmpty("+");
    private final static Component MINUS = Component.nullToEmpty("-");
    private static final WidgetSprites TEXTURES = new WidgetSprites(ResourceLocation.parse("widget/button"), ResourceLocation.parse("widget/button_disabled"), ResourceLocation.parse("widget/button_highlighted"));

    private final DoubleOption option;
    private int tooltipDelay;
    private Btn btn;
    private Btn selected;
    private int mouseDownTicks;

    private enum Btn {
        MINUS, PLUS
    }

    public DoubleButton(int x, int y, int width, int height, DoubleOption option) {
        super(x, y, width, height, option.text());
        this.option = option;
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (this.isHovered) {
            if (mouseX >= this.getX() && mouseX < this.getX() + this.height) {
                this.btn = Btn.MINUS;
            } else if (mouseX >= this.getX() + this.width - this.height && mouseX < this.getX() + this.width) {
                this.btn = Btn.PLUS;
            } else {
                onRelease(0, 0);
            }
        } else {
            onRelease(0, 0);
        }

        drawButton(context, MINUS, this.getX(), this.btn == Btn.MINUS || this.selected == Btn.MINUS);
        drawButton(context, PLUS, this.getX() + this.width - this.height, this.btn == Btn.PLUS || this.selected == Btn.PLUS);

        context.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | Mth.ceil(this.alpha * 255.0f) << 24);

        if (this.isHovered) {
            this.renderTooltip(context, mouseX, mouseY);
        }
    }

    private void drawButton(GuiGraphics context, Component text, int x, boolean i) {
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        context.blitSprite(RenderType::guiTextured, TEXTURES.get(this.active, i), x, this.getY(), this.height, this.height);
        context.drawCenteredString(Minecraft.getInstance().font, text, x + this.height / 2, this.getY() + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | Mth.ceil(this.alpha * 255.0f) << 24);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible || !this.isValidClickButton(button)) {
            return false;
        }
        if (this.btn != null) {
            this.mouseDownTicks = 1;
            this.onClick(mouseX, mouseY);
            return true;
        }
        return isHoveredOrFocused();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (this.btn == null) {
            return;
        }
        switch (this.btn) {
            case MINUS -> addValue(-0.01D);
            case PLUS -> addValue(0.01D);
        }
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        this.btn = null;
        this.mouseDownTicks = 0;
        this.setFocused(false);
    }

    public void renderTooltip(GuiGraphics context, int mouseX, int mouseY) {
        if (this.tooltipDelay > 15 && Minecraft.getInstance().screen != null) {
            PoseStack matrices = context.pose();
            matrices.pushPose();
            matrices.translate(0, 0, -399);
            context.renderTooltip(Minecraft.getInstance().font, this.option.tooltip(), mouseX, mouseY);
            matrices.popPose();
        }
    }

// TODO: fix keyboard accessibility in mob settings
//
//    public boolean tab() {
//        if (isFocused()) {
//            if (this.btn == Btn.MINUS) {
//                this.selected = Btn.PLUS;
//                return true;
//            } else if (Screen.hasShiftDown() && this.btn == Btn.PLUS) {
//                this.selected = Btn.MINUS;
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput builder) {
        this.defaultButtonNarrationText(builder);
    }

    @Override
    public @NotNull Component getMessage() {
        return this.option.text();
    }

    @Override
    public void tick() {
        if (this.isHoveredOrFocused() && this.active) {
            this.tooltipDelay++;
        } else if (this.tooltipDelay > 0) {
            this.tooltipDelay = 0;
        }

        if (this.mouseDownTicks > 0) {
            this.mouseDownTicks++;
            if (this.mouseDownTicks > 10) {
                onClick(0, 0);
            }
        }
    }

    private void addValue(double value) {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.option.set(this.option.get() + value);
    }
}
