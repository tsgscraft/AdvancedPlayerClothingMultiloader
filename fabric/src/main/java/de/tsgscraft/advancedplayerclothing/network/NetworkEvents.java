package de.tsgscraft.advancedplayerclothing.network;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.network.clothing.*;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerPayload;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerReturnPayload;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkEvents {
    public static void registerPayloadTypes() {
        PayloadTypeRegistry.playC2S().register(
                SetClothingPayload.TYPE,
                SetClothingPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playC2S().register(
                FullClothingPayload.TYPE,
                FullClothingPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                SetClothingSyncPayload.TYPE,
                SetClothingSyncPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                FullClothingSyncPayload.TYPE,
                FullClothingSyncPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                ClothingSyncPacket.TYPE,
                ClothingSyncPacket.STREAM_CODEC
        );

        // Register test for server payloads
        PayloadTypeRegistry.playC2S().register(
                TestForServerPayload.TYPE,
                TestForServerPayload.STREAM_CODEC
        );

        PayloadTypeRegistry.playS2C().register(
                TestForServerReturnPayload.TYPE,
                TestForServerReturnPayload.STREAM_CODEC
        );
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(
                SetClothingSyncPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() ->
                            ClothingNetwork.receiveSetClothing(payload)
                    );
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                FullClothingSyncPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() ->
                            ClothingNetwork.receiveFullClothing(payload)
                    );
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                ClothingSyncPacket.TYPE,
                (payload, context) -> {
                    context.client().execute(() ->
                            ClothingNetwork.receiveClothingSync(payload)
                    );
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                TestForServerReturnPayload.TYPE,
                (payload, context) -> {
                    if (payload.data() == 1) {
                        REFERENCE.LOG.info("Compatible server detected!");
                        C_REFERENCE.compatibleServer = true;
                    }
                }
        );
    }

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(
                SetClothingPayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                                ClothingNetwork.receiveSetClothing(payload);
                                Services.NETWORK.sendToAllPlayers(
                                        context.player().getServer().getPlayerList().getPlayers(),
                                        new SetClothingSyncPayload(
                                                context.player().getUUID().toString(),
                                                payload.clothingType(),
                                                payload.clothingId()
                                        )
                                );
                            }
                    );
                }
        );

        ServerPlayNetworking.registerGlobalReceiver(
                FullClothingPayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                        ClothingNetwork.receiveFullClothing(payload);
                        Services.NETWORK.sendToAllPlayers(
                                context.player().getServer().getPlayerList().getPlayers(),
                                new FullClothingSyncPayload(payload.playerUUID(), payload.clothingData())
                        );
                    });
                }
        );

        ServerPlayNetworking.registerGlobalReceiver(
                TestForServerPayload.TYPE,
                (payload, context) -> {
                    context.server().execute(() -> {
                        // Send back a response to the client indicating that the server is compatible
                        TestForServerReturnPayload returnPayload = new TestForServerReturnPayload((byte) 1);
                        Services.NETWORK.sendToPlayer(context.player(), returnPayload);
                    });
                }
        );
    }
}