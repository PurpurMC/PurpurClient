package org.purpurmc.purpur.client.gui.screen;

import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.gui.screen.widget.Tickable;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractScreen extends OptionsSubScreen {
    public static final Component TITLE = Component.translatable("purpurclient.options.title");

    private final Screen parent;

    protected List<AbstractWidget> options;
    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, TITLE);
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.fillGradient(0, 0, this.width, this.height, 0x800F4863, 0x80370038);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
        PurpurClient.instance().getConfigManager().save();
    }

// TODO: fix keyboard accessibility in mob settings
//
//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        if (keyCode == GLFW.GLFW_KEY_TAB) {
//            for (Drawable drawable : this.options) {
//                if (drawable instanceof DoubleButton option) {
//                    if (option.tab()) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return super.keyPressed(keyCode, scanCode, modifiers);
//    }

    @Override
    public void tick() {
        if (this.options != null) {
            this.options.forEach(option -> {
                if (option instanceof Tickable tickable) {
                    tickable.tick();
                }
            });
        }
    }

    public void openScreen(Screen screen) {
        if (this.minecraft != null) {
            this.minecraft.setScreen(screen);
        }
    }
}
