package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.client.compat.FirstPersonMod;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import de.tsgscraft.advancedplayerclothing.platform.services.INetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class FabricNetworkHelper implements INetworkHelper {

    @Override
    public void sendToAllPlayers(ServerPlayer sender, CustomPacketPayload payload) {
        for (ServerPlayer player : Objects.requireNonNull(sender.getServer()).getPlayerList().getPlayers()) {
            if (ServerPlayNetworking.canSend(player, payload.type())) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    @Override
    public void sendToAllPlayers(CustomPacketPayload payload) {
        for (ServerPlayer player : Minecraft.getInstance().player.getServer().getPlayerList().getPlayers()) {
            if (ServerPlayNetworking.canSend(player, payload.type())) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        if (ServerPlayNetworking.canSend(player, payload.type())) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}
