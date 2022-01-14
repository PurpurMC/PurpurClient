package org.purpurmc.purpur.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.options.DoubleOption;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.entity.Seat;
import org.purpurmc.purpur.client.fake.FakePlayer;
import org.purpurmc.purpur.client.fake.FakeWorld;
import org.purpurmc.purpur.client.gui.screen.widget.DoubleButton;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.purpurmc.purpur.client.mixin.accessor.AccessEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessHoglin;
import org.purpurmc.purpur.client.mixin.accessor.AccessMagmaCube;
import org.purpurmc.purpur.client.mixin.accessor.AccessSlime;

import java.util.ArrayList;

public class MobScreen extends AbstractScreen {
    private final Mob mob;
    private final Text subtitle;

    private FakePlayer fakePlayer;
    private Entity fakeEntity;
    private boolean alreadyInit;

    private double mouseDownX = Double.MIN_VALUE;
    private double mouseDownY;

    private double previewX = -80;
    private double previewY = 200;
    private float previewYaw = -145;
    private float previewPitch = -20;
    private float previewZoom = 60;
    private float previewZoomMultiplier = 1.0F;

    private final Text noPreview = new TranslatableText("purpurclient.options.no-preview");
    private final Text notImplemented = new TranslatableText("purpurclient.options.not-implemented");

    public MobScreen(Screen parent, Mob mob) {
        super(parent);
        this.mob = mob;
        this.subtitle = new TranslatableText("purpurclient.options.seat.title", mob.getType().getName());
    }

    @Override
    public void init() {
        super.init();

        final Seat seat = PurpurClient.instance().getConfig().seats.getSeat(this.mob);

        this.options = new ArrayList<>();
        if (seat != null) {
            this.options.add(new DoubleButton(this.centerX + 70, 90, 100, 20, new DoubleOption("seat.x", () -> seat.x, (value) -> seat.x = value)));
            this.options.add(new DoubleButton(this.centerX + 70, 120, 100, 20, new DoubleOption("seat.y", () -> seat.y, (value) -> seat.y = value)));
            this.options.add(new DoubleButton(this.centerX + 70, 150, 100, 20, new DoubleOption("seat.z", () -> seat.z, (value) -> seat.z = value)));
        }

        this.options.forEach(this::addDrawableChild);

        if (this.alreadyInit) {
            // we only need to set everything up once
            // not every time the window resizes
            return;
        }
        this.alreadyInit = true;

        if (this.client == null || this.client.player == null) {
            // we need a client and player to draw a preview to...
            return;
        }

        FakeWorld fakeWorld = new FakeWorld();

        this.fakePlayer = new FakePlayer(fakeWorld, this.client.player);
        this.fakeEntity = this.mob.getType().create(fakeWorld);
        if (this.fakeEntity == null) {
            // we need an entity to ride
            this.fakePlayer = null;
            return;
        }
        this.fakePlayer.setNoGravity(true);
        this.fakeEntity.setNoGravity(true);

        // special cases
        if (this.fakeEntity instanceof BatEntity bat) {
            // wake bat up, no hanging upside down
            bat.setRoosting(false);
        } else if (this.fakeEntity instanceof EnderDragonEntity dragon) {
            // dragon is huge, reduce the zoom
            this.previewZoomMultiplier /= 3F;
            this.previewY -= 25;
            // slow wings down
            dragon.getPhaseManager().setPhase(PhaseType.HOVER);
        } else if (this.fakeEntity instanceof GhastEntity) {
            // ghast is huge, reduce the zoom
            this.previewZoomMultiplier /= 3F;
            this.previewY -= 50;
        } else if (this.fakeEntity instanceof GiantEntity) {
            // giant is huge, reduce the zoom
            this.previewZoomMultiplier /= 5F;
        } else if (this.fakeEntity instanceof WitherEntity) {
            // wither is huge, reduce the zoom
            this.previewZoomMultiplier /= 1.5F;
        } else if (this.fakeEntity instanceof MagmaCubeEntity magmaCube) {
            ((AccessMagmaCube) magmaCube).invokeSetSize(3, true);
        } else if (this.fakeEntity instanceof SlimeEntity slime) {
            ((AccessSlime) slime).invokeSetSize(3, true);
        }

        // mount on the entity
        this.fakePlayer.startRiding(this.fakeEntity, true);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HandledScreen.BACKGROUND_TEXTURE);

        if (this.fakePlayer != null && this.fakeEntity != null) {
            drawPreviewModel(this.fakePlayer, this.fakeEntity);
        } else {
            drawCenteredText(matrixStack, this.textRenderer, this.noPreview, this.centerX - 80, 125, 0xFFFFFFFF);
        }

        matrixStack.push();
        matrixStack.translate(0, 0, 900);
        drawCenteredText(matrixStack, this.textRenderer, this.title, this.centerX, 15, 0xFFFFFFFF);
        drawCenteredText(matrixStack, this.textRenderer, this.subtitle, this.centerX, 30, 0xFFFFFFFF);
        if (this.options == null || this.options.isEmpty()) {
            drawCenteredText(matrixStack, this.textRenderer, this.notImplemented, this.centerX + 120, 125, 0xFFFFFFFF);
        } else {
            for (Drawable drawable : this.options) {
                drawable.render(matrixStack, mouseX, mouseY, delta);
            }
        }
        matrixStack.pop();
    }

    public void drawPreviewModel(FakePlayer player, Entity vehicle) {
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(this.centerX + this.previewX, this.previewY, 1500);
        matrixStack.scale(1.0F, 1.0F, -1.0F);

        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        float zoom = this.previewZoom * this.previewZoomMultiplier;
        matrixStack2.scale(zoom, zoom, zoom);

        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(this.previewPitch);
        Quaternion quaternion3 = Vec3f.POSITIVE_Y.getDegreesQuaternion(-this.previewYaw);
        quaternion2.hamiltonProduct(quaternion3);
        quaternion.hamiltonProduct(quaternion2);
        quaternion2.conjugate();
        matrixStack2.multiply(quaternion);

        DiffuseLighting.method_34742();
        EntityRenderDispatcher renderer = MinecraftClient.getInstance().getEntityRenderDispatcher();
        renderer.setRotation(quaternion2);
        renderer.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        fixEntityRender(vehicle, matrixStack2, () -> renderer.render(vehicle, vehicle.getX(), vehicle.getY(), vehicle.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0));
        renderer.render(player, player.getX(), player.getY(), player.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0);

        immediate.draw();
        renderer.setRenderShadows(true);
        matrixStack.pop();

        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    private void fixEntityRender(Entity entity, MatrixStack matrixStack, Runnable runnable) {
        if (entity instanceof EnderDragonEntity) {
            // dragon model is backwards, flip it
            matrixStack.push();
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-180.0F));
            runnable.run();
            matrixStack.pop();
        } else {
            runnable.run();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        this.mouseDownX = mouseX;
        this.mouseDownY = mouseY;
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        this.mouseDownX = 0;
        this.mouseDownY = 0;
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        }
        if (this.mouseDownX == Double.MIN_VALUE) {
            // mouse was already down from previous screen, ignore
            return false;
        }
        if (button == 0) {
            this.previewYaw -= (mouseX - this.mouseDownX);
            this.previewPitch -= (mouseY - this.mouseDownY);
            clampYawPitch();
        } else if (button == 1) {
            this.previewX += (mouseX - this.mouseDownX);
            this.previewY += (mouseY - this.mouseDownY);
        }
        this.mouseDownX = mouseX;
        this.mouseDownY = mouseY;
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.previewZoom += amount;
        clampZoom();
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fakeEntity != null) {
            // tick entity
            this.fakeEntity.resetPosition();
            ++this.fakeEntity.age;
            this.fakeEntity.tick();

            // prevent some logic from running by faking first tick every tick
            ((AccessEntity) this.fakeEntity).setFirstUpdate(true);

            // special cases that need to update every tick
            if (this.fakeEntity instanceof WaterCreatureEntity waterCreature) {
                // put water creatures in their natural habitat
                ((AccessEntity) waterCreature).setTouchingWater(true);
            } else if (this.fakeEntity instanceof StriderEntity strider) {
                // striders are naturally cold unless in the nether
                strider.setCold(false);
            } else if (this.fakeEntity instanceof HoglinEntity hoglin) {
                // hoglins will shake to convert if not in the nether
                ((AccessHoglin) hoglin).setTimeInOverworld(-1);
            } else if (this.fakeEntity instanceof AbstractPiglinEntity piglin) {
                // piglins and brutes will shake to convert if not in the nether
                ((AccessAbstractPiglin) piglin).setTimeInOverworld(-1);
            }
        }

        if (this.fakePlayer != null) {
            // tick player
            this.fakePlayer.resetPosition();
            ++this.fakePlayer.age;
            this.fakePlayer.tickRiding();

            // fix player yaw
            this.fakePlayer.setYaw(0);
            this.fakePlayer.prevYaw = 0;
            this.fakePlayer.setBodyYaw(0);
            this.fakePlayer.prevBodyYaw = 0;
            this.fakePlayer.setHeadYaw(0);
            this.fakePlayer.prevHeadYaw = 0;
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        if (this.fakePlayer != null) {
            this.fakePlayer.stopRiding();
        }
    }

    private void clampYawPitch() {
        if (this.previewPitch < -45.0) {
            this.previewPitch = -45.0F;
        } else if (this.previewPitch > 45.0) {
            this.previewPitch = 45.0F;
        }
        while (this.previewYaw < -360) {
            this.previewYaw += 360;
        }
        while (this.previewYaw > 360) {
            this.previewYaw -= 360;
        }
    }

    private void clampZoom() {
        if (this.previewZoom < 10.0F) {
            this.previewZoom = 10.0F;
        } else if (this.previewZoom > 100.0F) {
            this.previewZoom = 100.0F;
        }
    }
}
