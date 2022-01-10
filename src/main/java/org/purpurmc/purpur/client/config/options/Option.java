package org.purpurmc.purpur.client.config.options;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

@SuppressWarnings("unused")
public interface Option<T> {
    String key();

    Text text();

    List<OrderedText> tooltip();

    T get();

    void set(T value);
}
