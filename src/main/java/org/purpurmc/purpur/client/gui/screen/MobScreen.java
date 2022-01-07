package org.purpurmc.purpur.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.purpurmc.purpur.client.entity.Mob;

import static net.minecraft.client.gui.screen.ingame.HandledScreen.BACKGROUND_TEXTURE;

public class MobScreen extends AbstractScreen {
    private final Mob mob;
    private ClientPlayerEntity fakePlayer;

    public MobScreen(Screen parent, Mob mob) {
        super(parent, new TranslatableText("purpur-client.options.title"));
        this.mob = mob;
    }

    @Override
    public void init() {
        super.init();
        if (this.client != null && this.client.player != null) {
            this.fakePlayer = new ClientPlayerEntity(this.client, (ClientWorld) this.client.player.world, this.client.player.networkHandler, this.client.player.getStatHandler(), this.client.player.getRecipeBook(), false, false);
            this.fakePlayer.input = this.client.player.input;
            this.fakePlayer.startRiding(this.mob.getType().create(this.fakePlayer.world), true);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        super.render(matrixStack, mouseX, mouseY, delta);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

        if (fakePlayer != null) {
            drawEntity(100, 200, 60, mouseX, mouseY, this.fakePlayer);
        } else {
            // show text warning about needing a world for preview
        }
    }

    public void drawEntity(int x, int y, int size, float mouseX, float mouseY, ClientPlayerEntity player) {
        float f = (float) Math.atan(mouseX / 40.0F);
        float g = (float) Math.atan(mouseY / 40.0F);
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x, y, 1050.0D);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0D, 0.0D, 1000.0D);
        matrixStack2.scale((float) size, (float) size, (float) size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float h = player.bodyYaw;
        float i = player.getYaw();
        float j = player.getPitch();
        float k = player.prevHeadYaw;
        float l = player.headYaw;
        player.bodyYaw = 180.0F + f * 20.0F;
        player.setYaw(180.0F + f * 40.0F);
        player.setPitch(-g * 20.0F);
        player.headYaw = player.getYaw();
        player.prevHeadYaw = player.getYaw();
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        //RenderSystem.runAsFancy(() -> {
        entityRenderDispatcher.render(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack2, immediate, 15728880);
        entityRenderDispatcher.render(player.getVehicle(), 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack2, immediate, 15728880);
        //});
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        player.bodyYaw = h;
        player.setYaw(i);
        player.setPitch(j);
        player.prevHeadYaw = k;
        player.headYaw = l;
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
