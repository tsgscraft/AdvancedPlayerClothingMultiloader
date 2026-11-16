package de.tsgscraft.advancedplayerclothing.network;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.network.clothing.*;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;

import java.util.Map;
import java.util.UUID;

public class ClothingNetwork {
    private ClothingNetwork() {
    }

    public static void sendSetClothing(String clothingType, String clothingId) {
        System.out.println("Sending set clothing packet: " + clothingType + ", " + clothingId);
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.getConnection() == null) {
            return;
        }

        if (!REFERENCE.compatibleServer) return;

        LocalPlayer player = minecraft.player;
        if (player == null) {
            System.out.println("Player is null, cannot send set clothing packet.");
            return;
        }
        UUID playerUUID = player.getUUID();

        SetClothingPayload payload =
                new SetClothingPayload(
                        playerUUID.toString(),
                        clothingType,
                        clothingId
                );

        Services.NETWORK.sendToServer(payload);
        System.out.println("Sent set clothing packet: " + clothingType + ", " + clothingId);
    }

    public static void receiveSetClothing(SetClothingSyncPayload payload) {
        UUID uuid = UUID.fromString(payload.playerUUID());
        if (uuid.equals(Minecraft.getInstance().player.getUUID())) {
            return;
        }
        ClothingRegistry.getInstance().setClothing(uuid, payload.clothingType(), payload.clothingId(), false);
    }

    public static void receiveSetClothing(SetClothingPayload payload) {
        UUID uuid = UUID.fromString(payload.playerUUID());
        ClothingRegistry.getInstance().setClothing(uuid, payload.clothingType(), payload.clothingId(), false);
    }

    public static void sendFullClothing() {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.getConnection() == null) {
            return;
        }

        if (!REFERENCE.compatibleServer) return;

        FullClothingPayload payload =
                new FullClothingPayload(
                        Minecraft.getInstance().player.getUUID().toString(),
                        ClothingRegistry.getInstance().getActiveClothingElements()
                );
        System.out.println("Sending full clothing payload with " + payload.clothingData().size() + " entries.");

        Services.NETWORK.sendToServer(payload);
    }

    public static void receiveFullClothing(FullClothingSyncPayload payload) {
        UUID uuid = UUID.fromString(payload.playerUUID());
        if (uuid.equals(Minecraft.getInstance().player.getUUID())) {
            return;
        }
        ClothingRegistry.getInstance().setActiveClothingData(uuid, payload.clothingData());
    }

    public static void receiveFullClothing(FullClothingPayload payload) {
        UUID uuid = UUID.fromString(payload.playerUUID());
        ClothingRegistry.getInstance().setActiveClothingData(uuid, payload.clothingData());
    }

    public static void receiveClothingSync(ClothingSyncPacket payload) {
        REFERENCE.LOG.info("Received clothing sync packet with {} entries.", payload.activeClothingElementsByPlayer().size());
        Map<String, Map<String, String>> clothingData = payload.activeClothingElementsByPlayer();
        for (Map.Entry<String, Map<String, String>> entry : clothingData.entrySet()) {
            UUID uuid = UUID.fromString(entry.getKey());
            if (uuid.equals(Minecraft.getInstance().player.getUUID())) {
                continue;
            }
            ClothingRegistry.getInstance().setActiveClothingData(uuid, entry.getValue());
        }
    }
}
