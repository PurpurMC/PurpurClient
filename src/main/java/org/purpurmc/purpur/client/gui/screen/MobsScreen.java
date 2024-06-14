package org.purpurmc.purpur.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MobsList;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.widget.MobButton;

public class MobsScreen extends OptionsSubScreen {
    public final static MutableComponent MOBS_BTN = Component.translatable("purpurclient.options.mobs");

    protected OptionsList options;
    protected int centerX;

    public MobsScreen(Screen screen) {
        super(screen, Minecraft.getInstance().options, MOBS_BTN);
    }

    @Override
    protected void addOptions() {
        MobsList widget = new MobsList(this.minecraft, this.height, this);

        int amount = 15;
        List<AbstractWidget> list = new ArrayList<>();
        for (Mob mob : Mob.values()) {
            list.add(new MobButton(this.minecraft, this, mob));
            if (list.size() == amount) {
                widget.addEntry(list);
                list.clear();
            }
        }
        widget.addEntry(list);
        this.options = this.addRenderableWidget(widget);
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.fillGradient(0, 0, this.width, this.height, 0x800F4863, 0x80370038);
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        this.options.updateSize(this.width, this.layout);
    }
}
