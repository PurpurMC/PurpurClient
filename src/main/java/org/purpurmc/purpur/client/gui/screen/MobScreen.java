package org.purpurmc.purpur.client.gui.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EntitySpawnReason;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.purpurmc.purpur.client.PurpurClient;
import org.purpurmc.purpur.client.config.options.DoubleOption;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.entity.Seat;
import org.purpurmc.purpur.client.fake.FakePlayer;
import org.purpurmc.purpur.client.gui.screen.widget.DoubleButton;
import org.purpurmc.purpur.client.mixin.accessor.AccessAbstractPiglin;
import org.purpurmc.purpur.client.mixin.accessor.AccessEntity;
import org.purpurmc.purpur.client.mixin.accessor.AccessHoglin;
import org.purpurmc.purpur.client.mixin.accessor.AccessMagmaCube;
import org.purpurmc.purpur.client.mixin.accessor.AccessSlime;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.warden.Warden;

public class MobScreen extends AbstractScreen {
    private final Mob mob;
    private final Component subtitle;

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

    private final Component noPreview = Component.translatable("purpurclient.options.no-preview");
    private final Component notImplemented = Component.translatable("purpurclient.options.not-implemented");
    private final Component experimentDisabled = Component.translatable("purpurclient.options.experiment-disabled");

    public MobScreen(Screen parent, Mob mob) {
        super(parent);
        this.mob = mob;
        this.subtitle = Component.translatable("purpurclient.options.seat.title", mob.getType().getDescription());
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

        this.options.forEach(this::addRenderableWidget);

        if (this.alreadyInit) {
            // we only need to set everything up once
            // not every time the window resizes
            return;
        }
        this.alreadyInit = true;

        if (this.minecraft == null || this.minecraft.player == null) {
            // we need a client and player to draw a preview to...
            return;
        }

        this.fakePlayer = new FakePlayer(this.minecraft.level, this.minecraft.player);
        this.fakeEntity = this.mob.getType().create(this.minecraft.level, EntitySpawnReason.NATURAL);
        if (this.fakeEntity == null) {
            // we need an entity to ride
            this.fakePlayer = null;
            return;
        }
        this.fakePlayer.setNoGravity(true);
        this.fakeEntity.setNoGravity(true);

        // special cases
        if (this.fakeEntity instanceof Bat bat) {
            // wake bat up, no hanging upside down
            bat.setResting(false);
        } else if (this.fakeEntity instanceof EnderDragon dragon) {
            // dragon is huge, reduce the zoom
            this.previewZoomMultiplier /= 3F;
            this.previewY -= 25;
            // slow wings down
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
        } else if (this.fakeEntity instanceof Ghast) {
            // ghast is huge, reduce the zoom
            this.previewZoomMultiplier /= 3F;
            this.previewY -= 50;
        } else if (this.fakeEntity instanceof Giant) {
            // giant is huge, reduce the zoom
            this.previewZoomMultiplier /= 5F;
        } else if (this.fakeEntity instanceof Warden) {
            // warden is huge, reduce the zoom
            this.previewZoomMultiplier /= 1.5F;
        } else if (this.fakeEntity instanceof WitherBoss) {
            // wither is huge, reduce the zoom
            this.previewZoomMultiplier /= 1.5F;
        } else if (this.fakeEntity instanceof MagmaCube magmaCube) {
            ((AccessMagmaCube) magmaCube).invokeSetSize(3, true);
        } else if (this.fakeEntity instanceof Slime slime) {
            ((AccessSlime) slime).invokeSetSize(3, true);
        }

        // mount on the entity
        this.fakePlayer.startRiding(this.fakeEntity, true);
    }

    @Override
    protected void addOptions() {
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (this.minecraft.level == null) {
            super.renderPanorama(context, delta);
        } else {
            super.renderBlurredBackground();
        }
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AbstractContainerScreen.INVENTORY_LOCATION);

        if (this.fakePlayer != null && this.fakeEntity != null) {
            drawPreviewModel(this.fakePlayer, this.fakeEntity);
        } else {
            if (this.minecraft.level != null) {
                context.drawCenteredString(this.font, this.experimentDisabled, this.centerX - 80, 125, 0xFFFFFFFF);
            } else {
                context.drawCenteredString(this.font, this.noPreview, this.centerX - 80, 125, 0xFFFFFFFF);
            }
        }

        PoseStack matrices = context.pose();
        matrices.pushPose();
        matrices.translate(0, 0, 900);
        context.drawCenteredString(this.font, this.title, this.centerX, 15, 0xFFFFFFFF);
        context.drawCenteredString(this.font, this.subtitle, this.centerX, 30, 0xFFFFFFFF);
        if (this.options == null || this.options.isEmpty()) {
            context.drawCenteredString(this.font, this.notImplemented, this.centerX + 120, 125, 0xFFFFFFFF);
        } else {
            for (Renderable drawable : this.options) {
                drawable.render(context, mouseX, mouseY, delta);
            }
        }
        matrices.popPose();
        context.fillGradient(0, 0, this.width, this.height, 0x800F4863, 0x80370038);
    }

    public void drawPreviewModel(FakePlayer player, Entity vehicle) {
        Matrix4fStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.pushMatrix();
        matrixStack.translate((float) (this.centerX + this.previewX), (float) this.previewY, 1500);
        matrixStack.scale(1.0F, 1.0F, -1.0F);


        PoseStack matrixStack2 = new PoseStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        float zoom = this.previewZoom * this.previewZoomMultiplier;
        matrixStack2.scale(zoom, zoom, zoom);

        Quaternionf quaternion = Axis.ZP.rotationDegrees(-180.0F);
        Quaternionf quaternion2 = Axis.XP.rotationDegrees(this.previewPitch);
        Quaternionf quaternion3 = Axis.YP.rotationDegrees(-this.previewYaw);
        quaternion2.mul(quaternion3);
        quaternion.mul(quaternion2);
        quaternion2.conjugate();
        matrixStack2.mulPose(quaternion);

        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher renderer = Minecraft.getInstance().getEntityRenderDispatcher();
        renderer.overrideCameraOrientation(quaternion2);
        renderer.setRenderShadow(false);
        MultiBufferSource.BufferSource immediate = Minecraft.getInstance().renderBuffers().bufferSource();

        Minecraft.getInstance().execute(() -> {
            fixEntityRender(vehicle, matrixStack2, () -> renderer.render(vehicle, vehicle.getX(), vehicle.getY(), vehicle.getZ(), 0.0F, matrixStack2, immediate, 0xF000F0));
            renderer.render(player, player.getX(), player.getY(), player.getZ(), 0.0F, matrixStack2, immediate, 0xF000F0);
        });

        immediate.endBatch();
        renderer.setRenderShadow(true);
        matrixStack.popMatrix();

        Lighting.setupFor3DItems();
    }

    private void fixEntityRender(Entity entity, PoseStack matrixStack, Runnable runnable) {
        if (entity instanceof EnderDragon) {
            // dragon model is backwards, flip it
            matrixStack.pushPose();
            matrixStack.mulPose(Axis.YP.rotationDegrees(-180.0F));
            runnable.run();
            matrixStack.popPose();
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
        if (mouseDownX != 0 && mouseDownY != 0) {
            // only move if the mouse wasn't just dragged
            if (button == 0) {
                this.previewYaw -= (float) (mouseX - this.mouseDownX);
                this.previewPitch -= (float) (mouseY - this.mouseDownY);
                clampYawPitch();
            } else if (button == 1) {
                this.previewX += mouseX - this.mouseDownX;
                this.previewY += mouseY - this.mouseDownY;
            }
        }

        this.mouseDownX = mouseX;
        this.mouseDownY = mouseY;
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.previewZoom += (float) verticalAmount;
        clampZoom();
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.fakeEntity != null) {
            // tick entity
            this.fakeEntity.setOldPosAndRot();
            ++this.fakeEntity.tickCount;
            this.fakeEntity.tick();

            // prevent some logic from running by faking first tick every tick
            ((AccessEntity) this.fakeEntity).setFirstTick(true);

            // special cases that need to update every tick
            if (this.fakeEntity instanceof WaterAnimal waterCreature) {
                // put water creatures in their natural habitat
                ((AccessEntity) waterCreature).setWasTouchingWater(true);
            } else if (this.fakeEntity instanceof Strider strider) {
                // striders are naturally cold unless in the nether
                strider.setSuffocating(false);
            } else if (this.fakeEntity instanceof Hoglin hoglin) {
                // hoglins will shake to convert if not in the nether
                ((AccessHoglin) hoglin).setTimeInOverworld(-1);
            } else if (this.fakeEntity instanceof AbstractPiglin piglin) {
                // piglins and brutes will shake to convert if not in the nether
                ((AccessAbstractPiglin) piglin).setTimeInOverworld(-1);
            }
        }

        if (this.fakePlayer != null) {
            // tick player
            this.fakePlayer.setOldPosAndRot();
            ++this.fakePlayer.tickCount;
            this.fakePlayer.rideTick();

            // fix player yaw
            this.fakePlayer.setYRot(0);
            this.fakePlayer.yRotO = 0;
            this.fakePlayer.setYBodyRot(0);
            this.fakePlayer.yBodyRotO = 0;
            this.fakePlayer.setYHeadRot(0);
            this.fakePlayer.yHeadRotO = 0;
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
