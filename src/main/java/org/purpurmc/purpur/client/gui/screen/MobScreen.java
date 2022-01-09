package org.purpurmc.purpur.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.purpurmc.purpur.client.entity.Mob;
import org.purpurmc.purpur.client.mixin.accessor.AbstractPiglin;
import org.purpurmc.purpur.client.mixin.accessor.Entity;
import org.purpurmc.purpur.client.mixin.accessor.Hoglin;

public class MobScreen extends AbstractScreen {
    private final Mob mob;

    private ClientPlayerEntity fakePlayer;
    private net.minecraft.entity.Entity fakeEntity;
    private boolean alreadyInit;

    private double mouseDownX = Double.MIN_VALUE;
    private double mouseDownY;

    private double previewX = 100;
    private double previewY = 200;
    private float previewYaw = -145;
    private float previewPitch = -20;
    private float previewZoom = 60;

    public MobScreen(Screen parent, Mob mob) {
        super(parent, new TranslatableText("purpurclient.options.title"));
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

        this.fakeEntity = this.mob.getType().create(this.fakePlayer.world);
        if (this.fakeEntity == null) {
            // we need an entity to ride
            this.fakePlayer = null;
            return;
        }

        // special cases
        if (this.fakeEntity instanceof BatEntity bat) {
            // wake bat up, no hanging upside down
            bat.setRoosting(false);
        } else if (this.fakeEntity instanceof EnderDragonEntity dragon) {
            // dragon is huge, reduce the zoom
            this.previewZoom /= 3F;
            this.previewY -= 25;
            // slow wings down
            dragon.getPhaseManager().setPhase(PhaseType.HOVER);
        } else if (this.fakeEntity instanceof GhastEntity) {
            // ghast is huge, reduce the zoom
            this.previewZoom /= 3F;
            this.previewY -= 50;
        } else if (this.fakeEntity instanceof GiantEntity) {
            // giant is huge, reduce the zoom
            this.previewZoom /= 5F;
        } else if (this.fakeEntity instanceof WitherEntity) {
            // wither is huge, reduce the zoom
            this.previewZoom /= 1.5F;
        }

        // mount on the entity
        this.fakePlayer.startRiding(this.fakeEntity, true);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, mouseX, mouseY, delta);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HandledScreen.BACKGROUND_TEXTURE);

        if (this.fakePlayer != null && this.fakeEntity != null) {
            drawPreviewModel(this.fakePlayer, this.fakeEntity);
        } else {
            // TODO show text warning about needing a world for preview
        }
    }

    public void drawPreviewModel(ClientPlayerEntity player, net.minecraft.entity.Entity vehicle) {
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

        RenderSystem.runAsFancy(() -> {
            fixEntityRender(vehicle, matrixStack2, () -> {
                entityRenderDispatcher.render(vehicle, vehicle.getX(), vehicle.getY(), vehicle.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0);
            });
            entityRenderDispatcher.render(player, player.getX(), player.getY(), player.getZ(), 0.0F, 1.0F, matrixStack2, immediate, 0xF000F0);
        });

        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        matrixStack.pop();

        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    private void fixEntityRender(net.minecraft.entity.Entity entity, MatrixStack matrixStack, Runnable runnable) {
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
        if (this.fakeEntity != null) {
            // tick entity
            this.fakeEntity.resetPosition();
            ++this.fakeEntity.age;
            this.fakeEntity.tick();

            // prevent some logic from running by faking first tick every tick
            ((Entity) this.fakeEntity).setFirstUpdate(true);

            // special cases that need to update every tick
            if (this.fakeEntity instanceof WaterCreatureEntity waterCreature) {
                // put water creatures in their natural habitat
                ((Entity) waterCreature).setTouchingWater(true);
            } else if (this.fakeEntity instanceof StriderEntity strider) {
                // striders are naturally cold unless in the nether
                strider.setCold(false);
            } else if (this.fakeEntity instanceof HoglinEntity hoglin) {
                // hoglins will shake to convert if not in the nether
                ((Hoglin) hoglin).setTimeInOverworld(-1);
            } else if (this.fakeEntity instanceof AbstractPiglinEntity piglin) {
                // piglins and brutes will shake to convert if not in the nether
                ((AbstractPiglin) piglin).setTimeInOverworld(-1);
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
            this.fakePlayer.renderYaw = 0;
            this.fakePlayer.lastRenderYaw = 0;
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
