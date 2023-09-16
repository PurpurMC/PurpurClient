package org.purpurmc.purpur.client.mixin;


import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Window.class)
public class MixinWindow {
    @ModifyVariable(method = "Lnet/minecraft/client/util/Window;setIcon(Lnet/minecraft/resource/ResourcePack;Lnet/minecraft/client/util/Icons;)V", at = @At("STORE"), ordinal = 0)
    private List<InputSupplier<InputStream>> modifyIconList(List<InputSupplier<InputStream>> list) {
        if (PurpurClient.instance().getConfig().useWindowTitle) {
            return PurpurClient.ICON_LIST;
        } else {
            return list;
        }
    }
}
