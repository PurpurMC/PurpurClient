package org.purpurmc.purpur.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.mixin.EntityAccessor;

import static net.minecraft.client.gui.screen.ingame.HandledScreen.BACKGROUND_TEXTURE;

public class MobScreen extends AbstractScreen {
    private final Mob mob;

    private FakePlayer fakePlayer;
    private boolean alreadyInit;

    private double mouseDownX = Double.MIN_VALUE;
    private double mouseDownY;

    private double previewX = 100;
    private double previewY = 200;
    private float previewYaw = -145;
    private float previewPitch = -20;
    private float previewZoom = 60;

    public MobScreen(Screen parent, Mob mob) {
        super(parent, new TranslatableText("purpur-client.options.title"));
        this.mob = mob;
    }

    @Override
    public void init() {
        super.init();

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

        this.fakePlayer = new FakePlayer(this.client, this.client.player);

        MobEntity entity = this.mob.getType().create(this.fakePlayer.world);
        if (entity == null) {
            // we need an entity to ride
            return;
        }

        this.fakePlayer.startRiding(entity, true);

        // special cases
        if (entity instanceof EnderDragonEntity dragon) {
            // dragon is huge, reduce the zoom
            this.previewZoom /= 3F;
            // slow wings down
            dragon.getPhaseManager().setPhase(PhaseType.HOVER);
        } else if (entity instanceof BatEntity bat) {
            // wake bat up, no hanging upside down
            bat.setRoosting(false);
        }

        // add the entity to world so it can tick animations
        ((ClientWorld) this.fakePlayer.world).addEntity(entity.getId(), entity);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, mouseX, mouseY, delta);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

        if (this.fakePlayer != null && this.fakePlayer.hasVehicle()) {
            drawPreviewModel(this.fakePlayer, this.fakePlayer.getVehicle());
        } else {
            // show text warning about needing a world for preview
        }
    }

    public void drawPreviewModel(FakePlayer player, Entity vehicle) {
        // special cases that need to update every tick
        if (vehicle instanceof WaterCreatureEntity waterCreature) {
            // put water creatures in their natural habitat
            ((EntityAccessor) waterCreature).setTouchingWater(true);
        }

        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(this.previewX, this.previewY, 1500);
        matrixStack.scale(1.0F, 1.0F, -1.0F);

        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale(this.previewZoom, this.previewZoom, this.previewZoom);

        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(this.previewPitch);
        Quaternion quaternion3 = Vec3f.POSITIVE_Y.getDegreesQuaternion(-this.previewYaw);
        quaternion2.hamiltonProduct(quaternion3);
        quaternion.hamiltonProduct(quaternion2);
        quaternion2.conjugate();
        matrixStack2.multiply(quaternion);

        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        entityRenderDispatcher.render(vehicle, vehicle.getX(), vehicle.getY(), vehicle.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0);
        entityRenderDispatcher.render(player, player.getX(), player.getY(), player.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0);

        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        matrixStack.pop();

        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
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
        super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
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
    }

    @Override
    public void onClose() {
        super.onClose();
        if (this.fakePlayer != null) {
            // let's remove the fake entity from the world
            Entity entity = this.fakePlayer.getVehicle();
            if (entity != null) {
                this.fakePlayer.stopRiding();
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
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

    private static class FakePlayer extends ClientPlayerEntity {
        public FakePlayer(MinecraftClient client, ClientPlayerEntity player) {
            super(client, (ClientWorld) player.world, player.networkHandler, player.getStatHandler(), player.getRecipeBook(), false, false);
            this.input = new KeyboardInput(null) {
                @Override
                public void tick(boolean slowDown) {
                    // no inputs for fake player, or it will send packets to the server
                }
            };
        }

        @Override
        public boolean shouldRenderName() {
            // don't draw the nameplate over the player's head
            return false;
        }
    }
}
