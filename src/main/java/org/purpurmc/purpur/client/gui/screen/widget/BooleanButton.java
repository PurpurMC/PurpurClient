package org.purpurmc.purpur.client.gui.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.config.options.BooleanOption;

public class BooleanButton extends ButtonWidget implements Tickable {
    private final BooleanOption option;
    private int tooltipDelay;

    public BooleanButton(int x, int y, int width, int height, BooleanOption option) {
        super(x, y, width, height, option.text(), (button) -> option.toggle(), DEFAULT_NARRATION_SUPPLIER);
        this.option = option;
    }

    public void renderTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.hovered && this.tooltipDelay > 15 && MinecraftClient.getInstance().currentScreen != null) {
            MinecraftClient.getInstance().currentScreen.renderOrderedTooltip(matrixStack, this.option.tooltip(), mouseX, mouseY);
        }
    }

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
        if (this.isHovered() && this.active) {
            this.tooltipDelay++;
        } else if (this.tooltipDelay > 0) {
            this.tooltipDelay = 0;
        }
    }
}
