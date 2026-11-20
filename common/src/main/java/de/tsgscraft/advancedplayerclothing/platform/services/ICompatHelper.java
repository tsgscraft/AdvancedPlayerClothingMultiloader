package de.tsgscraft.advancedplayerclothing.platform.services;

import net.minecraft.server.level.ServerPlayer;

public interface ICompatHelper {

    // First Person Mod
    boolean shouldSkinHead();

    // Sable Ragdoll
    boolean isPlayerRagdoll(ServerPlayer player);
}
