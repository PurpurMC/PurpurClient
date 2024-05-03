package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class MobsList extends OptionsList {
    private static final int BIG_BUTTON_WIDTH = 310;
    private static final int DEFAULT_ITEM_HEIGHT = 25;
    private final OptionsSubScreen screen;

    public MobsList(Minecraft minecraft, int width, int height, OptionsSubScreen screen) {
        super(minecraft, width, height, screen);
        this.screen = screen;
    }

    public void addEntry(List<AbstractWidget> list) {
        this.addEntry(new MobsList.MobEntry(ImmutableList.copyOf(list), screen));
    }

    protected static class MobEntry extends OptionsList.Entry {
        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 20;

        MobEntry(List<AbstractWidget> children, Screen screen) {
            super(children, screen);
            this.screen = screen;
            this.children = children;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            int i = 0;
            int j = this.screen.width / 2 - 155;

            for(AbstractWidget abstractWidget : this.children) {
                abstractWidget.setPosition(j + i, top);
                abstractWidget.render(guiGraphics, mouseX, mouseY, partialTick);
                i += X_OFFSET;
            }
        }
    }
}
