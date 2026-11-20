package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.client.compat.FirstPersonMod;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import net.minecraft.server.level.ServerPlayer;

public class FabricCompatHelper implements ICompatHelper {
    @Override
    public boolean shouldSkinHead() {
        return FirstPersonMod.shouldSkipHead();
    }

    @Override
    public boolean isPlayerRagdoll(ServerPlayer player) {
        return false;
    }
}
