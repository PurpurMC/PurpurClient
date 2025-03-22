package org.purpurmc.purpur.client.gui.screen;

import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.Config;
import org.purpurmc.purpur.client.config.options.BooleanOption;
import org.purpurmc.purpur.client.config.options.Button;
import org.purpurmc.purpur.client.gui.screen.widget.BooleanButton;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class OptionsScreen extends Screen {
    public static final Component TITLE = Component.translatable("purpurclient.options.title");
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    private final Screen parent;
    protected int centerX;

    public OptionsScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    protected void init() {
        LinearLayout linearLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(8));
        linearLayout.addChild(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);

        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();

        final Config config = PurpurClient.instance().getConfig();

        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
        rowHelper.addChild(new BooleanButton(this.centerX - 160, 50, 150, 20, new BooleanOption("bee-count-in-debug", () -> config.beeCountInDebug, (value) -> config.beeCountInDebug = value)));
        rowHelper.addChild(new BooleanButton(this.centerX + 10, 50, 150, 20, new BooleanOption("splash-screen", () -> config.useSplashScreen, (value) -> config.useSplashScreen = value)));
        rowHelper.addChild(new BooleanButton(this.centerX - 160, 80, 150, 20, new BooleanOption("window-title", () -> config.useWindowTitle, (value) -> {
            config.useWindowTitle = value;
            PurpurClient.instance().updateTitle();
        })));
        rowHelper.addChild(new Button(this.centerX + 10, 80, 150, 20, MobsScreen.MOBS_BTN, button -> this.minecraft.setScreen(new MobsScreen(this))));

        this.layout.addToContents(gridLayout);
        this.layout.addToFooter(net.minecraft.client.gui.components.Button.builder(CommonComponents.GUI_DONE, buttonx -> this.onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.fillGradient(0, 0, this.width, this.height, 0x800F4863, 0x80370038);
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
        PurpurClient.instance().getConfigManager().save();
    }
}
