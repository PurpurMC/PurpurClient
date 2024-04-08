package org.purpurmc.purpur.client.config.options;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

@SuppressWarnings("unused")
public interface Option<T> {
    String key();

    Component text();

    List<FormattedCharSequence> tooltip();

    T get();

    void set(T value);
}
