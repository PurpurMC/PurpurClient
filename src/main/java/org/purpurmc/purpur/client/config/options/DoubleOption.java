package org.purpurmc.purpur.client.config.options;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class DoubleOption implements Option<Double> {
    private final String key;
    private final List<FormattedCharSequence> tooltip;
    private final Getter getter;
    private final Setter setter;

    private Component text;

    public DoubleOption(String key, Getter getter, Setter setter) {
        this.key = "purpurclient.options." + key;
        this.tooltip = Minecraft.getInstance().font.split(Component.translatable(this.key + ".tooltip"), 170);
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
    public List<FormattedCharSequence> tooltip() {
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
