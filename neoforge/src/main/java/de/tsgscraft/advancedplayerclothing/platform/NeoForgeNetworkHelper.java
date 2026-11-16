package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.client.compat.FirstPersonMod;
import de.tsgscraft.advancedplayerclothing.client.compat.RagdollMod;
import de.tsgscraft.advancedplayerclothing.network.clothing.ClothingSyncPacket;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import de.tsgscraft.advancedplayerclothing.platform.services.INetworkHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHelper implements INetworkHelper {

    @Override
    public void sendToAllPlayers(ServerPlayer sender, CustomPacketPayload payload) {
        PacketDistributor.sendToAllPlayers(
                payload
        );
    }

    @Override
    public void sendToAllPlayers(CustomPacketPayload payload) {
        PacketDistributor.sendToAllPlayers(
                payload
        );
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
