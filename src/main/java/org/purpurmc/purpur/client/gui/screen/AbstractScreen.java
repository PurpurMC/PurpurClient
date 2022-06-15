package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.gui.screen.widget.DoubleButton;
import org.purpurmc.purpur.client.gui.screen.widget.Tickable;

import java.util.List;

public abstract class AbstractScreen extends Screen {
    private final Screen parent;

    protected List<ClickableWidget> options;
    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(Text.translatable("purpurclient.options.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);
        drawCenteredText(matrixStack, this.textRenderer, this.title, this.centerX, 15, 0xFFFFFFFF);
        super.render(matrixStack, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack, int vOffset) {
        if (this.client != null && this.client.world != null) {
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0xF00F4863, 0xF0370038);
        } else {
            this.renderBackgroundTexture(vOffset);
        }
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
        PurpurClient.instance().getConfigManager().save();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_TAB) {
            for (Drawable drawable : this.options) {
                if (drawable instanceof DoubleButton option) {
                    if (option.tab()) {
                        return false;
                    }
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

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
        if (this.client != null) {
            this.client.setScreen(screen);
        }
    }
}
