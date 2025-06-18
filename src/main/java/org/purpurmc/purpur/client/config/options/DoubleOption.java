package org.purpurmc.purpur.client.config.options;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

public class DoubleOption implements Option<Double> {
    private final String key;
    private final List<ClientTooltipComponent> tooltip = new ArrayList<>();
    private final Getter getter;
    private final Setter setter;

    private Component text;

    public DoubleOption(String key, Getter getter, Setter setter) {
        this.key = "purpurclient.options." + key;
        Minecraft.getInstance().font.split(Component.translatable(this.key + ".tooltip"), 170).forEach(splitTooltip -> {
            tooltip.add(ClientTooltipComponent.create(splitTooltip));
        });
        this.getter = getter;
        this.setter = setter;

        this.set(this.get());
    }

    @Override
    @SuppressWarnings("unused")
    public String key() {
        return this.key;
    }

    @Override
    public Component text() {
        return this.text;
    }

    @Override
    public List<ClientTooltipComponent> tooltip() {
        return this.tooltip;
    }

    @Override
    public Double get() {
        return this.getter.get();
    }

    @Override
    public void set(Double value) {
        this.setter.set(Math.round(value * 100.0) / 100.0);
        this.text = Component.translatable(this.key, String.format("%.2f", get()));
    }

    @FunctionalInterface
    public interface Getter {
        double get();
    }

    @FunctionalInterface
    public interface Setter {
        void set(double value);
    }
}
