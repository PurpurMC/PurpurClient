package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.purpurmc.purpur.client.config.options.DoubleOption;

public class DoubleButton extends ClickableWidget implements Tickable {
    private final static Text PLUS = Text.of("+");
    private final static Text MINUS = Text.of("-");
    private static final ButtonTextures TEXTURES = new ButtonTextures(new Identifier("widget/button"), new Identifier("widget/button_disabled"), new Identifier("widget/button_highlighted"));

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
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.hovered) {
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

        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | MathHelper.ceil(this.alpha * 255.0f) << 24);

        if (this.hovered) {
            this.renderTooltip(context, mouseX, mouseY);
        }
    }

    private void drawButton(DrawContext context, Text text, int x, boolean i) {
        RenderSystem.enableDepthTest();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        context.drawGuiTexture(TEXTURES.get(this.active, i), x, this.getY(), this.height, this.height);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x + this.height / 2, this.getY() + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | MathHelper.ceil(this.alpha * 255.0f) << 24);
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
        return isSelected();
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

    public void renderTooltip(DrawContext context, int mouseX, int mouseY) {
        if (this.tooltipDelay > 15 && MinecraftClient.getInstance().currentScreen != null) {
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.translate(0, 0, -399);
            context.drawOrderedTooltip(MinecraftClient.getInstance().textRenderer, this.option.tooltip(), mouseX, mouseY);
            matrices.pop();
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
    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

    @Override
    public Text getMessage() {
        return this.option.text();
    }

    @Override
    public void tick() {
        if (this.isSelected() && this.active) {
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
        this.playDownSound(MinecraftClient.getInstance().getSoundManager());
        this.option.set(this.option.get() + value);
    }
}
