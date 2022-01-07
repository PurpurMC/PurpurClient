package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.BooleanOption;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.gui.screen.widget.BooleanButton;

import java.util.List;

public class OptionsScreen extends AbstractScreen {
    public OptionsScreen(Screen parent) {
        super(parent, Text.of("Purpur Client Options"));
    }

    @Override
    public void init() {
        super.init();

        Config config = PurpurClient.instance().getConfig();

        this.options = List.of(
                new BooleanButton(this.centerX - 160, 50, 150, 20, new BooleanOption(
                        "fix-chat-stutter",
                        config::isFixChatStutter,
                        config::setFixChatStutter)),
                new BooleanButton(this.centerX + 10, 50, 150, 20, new BooleanOption(
                        "bee-count-in-debug",
                        config::isBeeCountInDebug,
                        config::setBeeCountInDebug))
        );

        this.options.forEach(this::addDrawableChild);
    }
}
