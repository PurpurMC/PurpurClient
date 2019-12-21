package net.pl3x.fabric.purpurclient.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerEntityRenderer.class)
public abstract class MixinPlayerEntityRenderer extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public MixinPlayerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, PlayerEntityModel<AbstractClientPlayerEntity> entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    @Overwrite
    public void renderLabelIfPresent(final AbstractClientPlayerEntity entity, final String string, final MatrixStack arg2, final VertexConsumerProvider arg3, final int i) {
        final double d = this.renderManager.getSquaredDistanceToCamera(entity);
        arg2.push();
        if (d < 4096.0) {
            final Scoreboard lv = entity.getScoreboard();
            final ScoreboardObjective lv2 = lv.getObjectiveForSlot(2);
            if (lv2 != null) {
                final ScoreboardPlayerScore lv3 = lv.getPlayerScore(entity.getEntityName(), lv2);
                super.renderLabelIfPresent(entity, lv3.getScore() + " " + lv2.getDisplayName().asFormattedString(), arg2, arg3, i);
                final double x = 0.0;
                this.getFontRenderer().getClass();
                arg2.translate(x, 9.0f * 1.15f * 0.025f, 0.0);
            }
        }
        super.renderLabelIfPresent(entity, string, arg2, arg3, i);
        arg2.pop();
    }
}
