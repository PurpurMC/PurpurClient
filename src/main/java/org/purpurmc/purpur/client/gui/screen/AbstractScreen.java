package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.purpurmc.purpur.client.gui.screen.widget.Tickable;

import java.util.List;

public abstract class AbstractScreen extends Screen {
    private final Screen parent;

    protected List<ButtonWidget> options;
    protected int centerX;

    public AbstractScreen(Screen parent, Text title) {
        super(title);
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
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0xD00F4863, 0xC0370038);
        } else {
            this.renderBackgroundTexture(vOffset);
        }
    }

    @Override
    public void onClose() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
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

    public MinecraftClient getClient() {
        return this.client;
    }
}
