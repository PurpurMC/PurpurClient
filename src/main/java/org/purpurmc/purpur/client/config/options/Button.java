package org.purpurmc.purpur.client.config.options;

import net.minecraft.network.chat.Component;

public class Button extends net.minecraft.client.gui.components.Button {
    public Button(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }
}
