package org.purpurmc.purpur.client.config.options;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class DoubleOption implements Option<Double> {
    private final String key;
    private final List<OrderedText> tooltip;
    private final Getter getter;
    private final Setter setter;

    private Text text;

    public DoubleOption(String key, Getter getter, Setter setter) {
        this.key = "purpurclient.options." + key;
        this.tooltip = MinecraftClient.getInstance().textRenderer.wrapLines(new TranslatableText(this.key + ".tooltip"), 170);
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
    public Text text() {
        return this.text;
    }

    @Override
    public List<OrderedText> tooltip() {
        return this.tooltip;
    }

    @Override
    public Double get() {
        return this.getter.get();
    }

    @Override
    public void set(Double value) {
        this.setter.set(Math.round(value * 100.0) / 100.0);
        this.text = new TranslatableText(this.key, String.format("%.2f", get()));
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
