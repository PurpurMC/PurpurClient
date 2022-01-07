package org.purpurmc.purpur.client.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import com.mojang.authlib.yggdrasil.response.BlockListResponse;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.purpurmc.purpur.client.PurpurClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/*
 * MIT License
 *
 * Copyright (c) 2020 Fx Morin
 *
 * https://github.com/fxmorin/carpet-fixes/blob/3ce678fdd1afd32cd9fe88e06fa5562c0a03099d/src/main/java/carpetfixes/mixins/coreSystemFixes/YggdrasilUserApiService_syncBlockListMixin.java
 */

@Environment(EnvType.CLIENT)
@Mixin(YggdrasilUserApiService.class)
public abstract class MixinYggdrasilUserApiService {
    @Shadow
    protected abstract Set<UUID> fetchBlockList();

    @Shadow
    private Set<UUID> blockList;

    @Redirect(
            method = "isBlockedPlayer(Ljava/util/UUID;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/authlib/yggdrasil/YggdrasilUserApiService;fetchBlockList()Ljava/util/Set;"
            )
    )
    public Set<UUID> isBlockedPlayerFetchAsync(YggdrasilUserApiService instance) {
        if (PurpurClient.instance().getConfig().isFixChatStutter()) {
            CompletableFuture.runAsync(() -> this.blockList = this.fetchBlockList());
            return null; //Skip first laggy check. Although load the blocklist async for the next messages
        }
        return this.fetchBlockList();
    }


    @Redirect(
            method = "forceFetchBlockList()Ljava/util/Set;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/authlib/yggdrasil/response/BlockListResponse;getBlockedProfiles()Ljava/util/Set;"
            )
    )
    private Set<UUID> forceFetchBlockListDontReturnNull(BlockListResponse instance) {
        if (PurpurClient.instance().getConfig().isFixChatStutter()) { //Do not return null or else the system will think the blockList is not loaded
            Set<UUID> uuids = instance.getBlockedProfiles();
            return uuids == null ? new HashSet<>() : uuids;
        }
        return instance.getBlockedProfiles();
    }
}
