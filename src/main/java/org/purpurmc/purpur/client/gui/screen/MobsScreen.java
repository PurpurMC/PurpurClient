package org.purpurmc.purpur.client.gui.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.recipebook.SmokerRecipeBookScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.gui.screen.widget.MobButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MobsScreen extends AbstractScreen {
    public MobsScreen(Screen parent) {
        super(parent);
    }

    @Override
    public void init() {
        super.init();

        this.options = new ArrayList<>();
        int x = -7, y = 80;
        for (Mob mob : Mob.values()) {
            this.options.add(new MobButton(this, mob, this.centerX + x * 21 - 8, y));
            if (x++ >= 7) {
                x = -7;
                y += 20;
            }
        }

        this.options.forEach(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, OptionsScreen.MOBS_BTN, this.centerX, 30, 0xFFFFFFFF);
    }
}
