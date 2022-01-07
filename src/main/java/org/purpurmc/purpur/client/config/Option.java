package org.purpurmc.purpur.client.config;

import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

public interface Option<T> {
    String key();

    Text text();

    List<OrderedText> tooltip();

    T get();

    void set(T value);
}
