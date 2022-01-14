package org.purpurmc.purpur.client.config.options;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.function.Consumer;

public class Button extends ButtonWidget {
    public Button(int x, int y, int width, int height, TranslatableText message, PressAction onPress) {
        super(x, y, width, height, message, onPress, new ButtonWidget.TooltipSupplier() {
            private final Text text = new TranslatableText(message.getKey() + ".tooltip");

            @Override
            public void onTooltip(ButtonWidget buttonWidget, MatrixStack matrixStack, int mouseX, int mouseY) {
                MinecraftClient.getInstance().currentScreen.renderTooltip(matrixStack, text, mouseX, mouseY);
            }

            @Override
            public void supply(Consumer<Text> consumer) {
                consumer.accept(text);
            }
        });
    }
}
