package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.options.BooleanOption;
import org.purpurmc.purpur.client.config.options.Button;
import org.purpurmc.purpur.client.gui.screen.widget.BooleanButton;

import java.util.ArrayList;

public class OptionsScreen extends AbstractScreen {
    public final static MutableText MOBS_BTN = Text.translatable("purpurclient.options.mobs");

    public OptionsScreen(Screen parent) {
        super(parent);
    }

    @Override
    public void init() {
        super.init();

        final Config config = PurpurClient.instance().getConfig();

        this.options = new ArrayList<>();
        this.options.add(new BooleanButton(this.centerX - 160, 50, 150, 20, new BooleanOption("bee-count-in-debug", () -> config.beeCountInDebug, (value) -> config.beeCountInDebug = value)));
        this.options.add(new BooleanButton(this.centerX + 10, 50, 150, 20, new BooleanOption("splash-screen", () -> config.useSplashScreen, (value) -> config.useSplashScreen = value)));
        this.options.add(new BooleanButton(this.centerX - 160, 80, 150, 20, new BooleanOption("window-title", () -> config.useWindowTitle, (value) -> {
            config.useWindowTitle = value;
            PurpurClient.instance().updateTitle();
        })));
        this.options.add(new Button(this.centerX + 10, 80, 150, 20, MOBS_BTN, button -> openScreen(new MobsScreen(this))));

        this.options.forEach(this::addDrawableChild);
    }
}
