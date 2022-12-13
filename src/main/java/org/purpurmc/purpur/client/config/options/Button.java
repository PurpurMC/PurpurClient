package org.purpurmc.purpur.client.config.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class Button extends ButtonWidget {
    public Button(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }
}
