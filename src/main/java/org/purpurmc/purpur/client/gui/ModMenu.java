package org.purpurmc.purpur.client.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import org.purpurmc.purpur.client.gui.screen.OptionsScreen;

public class ModMenu implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OptionsScreen::new;
    }
}
