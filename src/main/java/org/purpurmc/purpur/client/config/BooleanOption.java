package org.purpurmc.purpur.client.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class BooleanOption implements Option<Boolean> {
    private final String key;
    private final List<OrderedText> tooltip;
    private final Getter getter;
    private final Setter setter;

    private final Text on;
    private final Text off;

    public BooleanOption(String key, Getter getter, Setter setter) {
        this.key = "purpur-client.options." + key;
        this.tooltip = MinecraftClient.getInstance().textRenderer.wrapLines(new TranslatableText(this.key + ".tooltip"), 170);
        this.on = new TranslatableText("options.on.composed", new TranslatableText(this.key));
        this.off = new TranslatableText("options.off.composed", new TranslatableText(this.key));
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public String key() {
        return this.key;
    }

    @Override
    public Text text() {
        return get() ? this.on : this.off;
    }

    @Override
    public List<OrderedText> tooltip() {
        return this.tooltip;
    }

    @Override
    public Boolean get() {
        return this.getter.get();
    }

    @Override
    public void set(Boolean value) {
        this.setter.set(value);
    }

    public void toggle() {
        set(!get());
    }

    @FunctionalInterface
    public interface Getter {
        boolean get();
    }

    @FunctionalInterface
    public interface Setter {
        void set(boolean value);
    }
}
