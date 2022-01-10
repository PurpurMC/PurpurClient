package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.options.BooleanOption;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.widget.BooleanButton;
import org.purpurmc.purpur.client.gui.screen.widget.MobButton;

import java.util.ArrayList;

public class OptionsScreen extends AbstractScreen {
    public OptionsScreen(Screen parent) {
        super(parent, Text.of("Purpur Client Options"));
    }

    @Override
    public void init() {
        super.init();

        final Config config = PurpurClient.instance().getConfig();

        this.options = new ArrayList<>();
        this.options.add(new BooleanButton(this.centerX - 160, 50, 150, 20, new BooleanOption("fix-chat-stutter", () -> config.fixChatStutter, (value) -> config.fixChatStutter = value)));
        this.options.add(new BooleanButton(this.centerX + 10, 50, 150, 20, new BooleanOption("bee-count-in-debug", () -> config.beeCountInDebug, (value) -> config.beeCountInDebug = value)));

        int x = -7, y = 100;
        for (Mob mob : Mob.values()) {
            this.options.add(new MobButton(this, mob, this.centerX + x * 21 - 8, y));
            if (x++ >= 7) {
                x = -7;
                y += 20;
            }
        }

        this.options.forEach(this::addDrawableChild);
    }
}
