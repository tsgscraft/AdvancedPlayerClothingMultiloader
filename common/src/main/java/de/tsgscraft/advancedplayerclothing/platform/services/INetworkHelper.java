package de.tsgscraft.advancedplayerclothing.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {

    // Always from server
    void sendToAllPlayers(ServerPlayer sender, CustomPacketPayload payload);
    void sendToAllPlayers(CustomPacketPayload payload);

    // Always from server
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    // Always from client
    void sendToServer(CustomPacketPayload payload);
}
