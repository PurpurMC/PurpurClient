package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.widget.MobButton;

public class RidablesScreen extends AbstractScreen {
    public RidablesScreen(Screen parent) {
        super(parent, new TranslatableText("purpur-client.options.title"));
    }

    @Override
    public void init() {
        super.init();

        this.addDrawableChild(new MobButton(this, Mob.AXOLOTL, 50, 50));
        this.addDrawableChild(new MobButton(this, Mob.BAT, 90, 50));
        this.addDrawableChild(new MobButton(this, Mob.BEE, 130, 50));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, mouseX, mouseY, delta);
        drawCenteredText(matrixStack, this.textRenderer, new TranslatableText("purpur-client.options.mod-settings"), this.centerX, 30, 0xFFFFFFFF);
    }

    @Override
    public void onClose() {
        // todo save config here
        super.onClose();
    }
}
