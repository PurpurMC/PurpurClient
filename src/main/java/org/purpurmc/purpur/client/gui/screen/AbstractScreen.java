package org.purpurmc.purpur.client.gui.screen;

import org.lwjgl.glfw.GLFW;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.gui.screen.widget.DoubleButton;
import org.purpurmc.purpur.client.gui.screen.widget.Tickable;

import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class AbstractScreen extends Screen {
    private final Screen parent;

    protected List<AbstractWidget> options;
    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(Component.translatable("purpurclient.options.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.drawCenteredString(this.font, this.title, this.centerX, 15, 0xFFFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (this.minecraft.level == null) {
            this.renderPanorama(context, delta);
        } else {
            context.fillGradient(0, 0, this.width, this.height, 0xF00F4863, 0xF0370038);
        }
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
