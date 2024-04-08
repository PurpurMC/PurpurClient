package org.purpurmc.purpur.client.mixin;


import com.mojang.blaze3d.platform.Window;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.packs.resources.IoSupplier;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Window.class)
public class MixinWindow {
    @ModifyVariable(method = "Lcom/mojang/blaze3d/platform/Window;setIcon(Lnet/minecraft/server/packs/PackResources;Lcom/mojang/blaze3d/platform/IconSet;)V", at = @At("STORE"), ordinal = 0)
    private List<IoSupplier<InputStream>> modifyIconList(List<IoSupplier<InputStream>> list) {
        if (PurpurClient.instance().getConfig().useWindowTitle) {
            return PurpurClient.ICON_LIST;
        } else {
            return list;
        }
    }
}
