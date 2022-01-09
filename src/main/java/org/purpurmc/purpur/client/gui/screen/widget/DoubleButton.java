package org.purpurmc.purpur.client.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.purpurmc.purpur.client.config.options.DoubleOption;

public class DoubleButton extends ClickableWidget implements Tickable {
    private final static Text PLUS = Text.of("+");
    private final static Text MINUS = Text.of("-");

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
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) {
            if (mouseX >= this.x && mouseX < this.x + this.height) {
                this.btn = Btn.MINUS;
            } else if (mouseX >= this.x + this.width - this.height && mouseX < this.x + this.width) {
                this.btn = Btn.PLUS;
            } else {
                onRelease(0, 0);
            }
        } else {
            onRelease(0, 0);
        }

        drawButton(matrixStack, MINUS, this.x, this.getYImage(this.btn == Btn.MINUS || this.selected == Btn.MINUS));
        drawButton(matrixStack, PLUS, this.x + this.width - this.height, this.getYImage(this.btn == Btn.PLUS || this.selected == Btn.PLUS));

        ClickableWidget.drawCenteredText(matrixStack, MinecraftClient.getInstance().textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | MathHelper.ceil(this.alpha * 255.0f) << 24);

        if (this.hovered) {
            this.renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    private void drawButton(MatrixStack matrixStack, Text text, int x, int i) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrixStack, x, this.y, 0, 46 + i * 20, this.height / 2, this.height);
        this.drawTexture(matrixStack, x + this.height / 2, this.y, 200 - this.height / 2, 46 + i * 20, this.height / 2, this.height);
        ClickableWidget.drawCenteredText(matrixStack, MinecraftClient.getInstance().textRenderer, text, x + this.height / 2, this.y + (this.height - 8) / 2, (this.active ? 0xFFFFFF : 0xA0A0A0) | MathHelper.ceil(this.alpha * 255.0f) << 24);
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
        return isHovered();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (this.btn == null) {
            return;
        }
        switch (this.btn) {
            case MINUS -> addValue(-0.01);
            case PLUS -> addValue(0.01);
        }
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        this.btn = null;
        this.mouseDownTicks = 0;
    }

    @Override
    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.tooltipDelay > 15 && MinecraftClient.getInstance().currentScreen != null) {
            MinecraftClient.getInstance().currentScreen.renderOrderedTooltip(matrixStack, this.option.tooltip(), mouseX, mouseY);
        }
    }

    public boolean tab() {
        if (isFocused()) {
            if (this.btn == Btn.MINUS) {
                this.selected = Btn.PLUS;
                return true;
            } else if (Screen.hasShiftDown() && this.btn == Btn.PLUS) {
                this.selected = Btn.MINUS;
                return true;
            }
        }
        return false;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
        builder.put(NarrationPart.HINT, this.getMessage());
    }

    @Override
    public Text getMessage() {
        return this.option.text();
    }

    @Override
    public void tick() {
        if (this.isHovered() && this.active) {
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
