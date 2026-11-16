package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.platform.services.INetworkHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class NeoForgeNetworkHelper implements INetworkHelper {

    @Override
    public void sendToAllPlayers(List<ServerPlayer> players, CustomPacketPayload payload) {
        for (ServerPlayer player : players) {
            PacketDistributor.sendToPlayer(
                    player,
                    payload
            );
        }
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(
                player,
                payload
        );
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(
                payload
        );
    }
}
