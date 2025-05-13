package org.purpurmc.purpur.client.config.options;

import java.util.List;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

@SuppressWarnings("unused")
public interface Option<T> {
    String key();

    Component text();

    List<ClientTooltipComponent> tooltip();

    T get();

    void set(T value);
}
