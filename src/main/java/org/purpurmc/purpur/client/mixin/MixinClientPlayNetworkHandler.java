package org.purpurmc.purpur.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler implements ClientPlayPacketListener {
    @Final
    @Shadow
    private MinecraftClient client;
    @Shadow
    private ClientWorld world;
    @Final
    @Shadow
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @reason Disable spammy warnings
     * @author BillyGalbreath
     */
    @Overwrite
    public void onEntityAttributes(EntityAttributesS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity != null) {
            if (!(entity instanceof LivingEntity)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
            } else {
                AttributeContainer attributeContainer = ((LivingEntity) entity).getAttributes();
                Iterator var4 = packet.getEntries().iterator();

                while (true) {
                    while (var4.hasNext()) {
                        net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket.Entry entry = (net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket.Entry) var4.next();
                        EntityAttributeInstance entityAttributeInstance = attributeContainer.getCustomInstance(entry.getId());
                        if (entityAttributeInstance == null) {
                            // this is super fucking annoying
                            LOGGER.warn("Entity {} does not have attribute {}", entity, Registry.ATTRIBUTE.getId(entry.getId()));
                        } else {
                            entityAttributeInstance.setBaseValue(entry.getBaseValue());
                            entityAttributeInstance.clearModifiers();
                            Iterator var7 = entry.getModifiers().iterator();

                            while (var7.hasNext()) {
                                EntityAttributeModifier entityAttributeModifier = (EntityAttributeModifier) var7.next();
                                entityAttributeInstance.addTemporaryModifier(entityAttributeModifier);
                            }
                        }
                    }

                    return;
                }
            }
        }
    }
}
