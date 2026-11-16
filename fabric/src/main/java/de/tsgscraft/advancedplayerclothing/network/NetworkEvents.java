package de.tsgscraft.advancedplayerclothing.network;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.network.clothing.*;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerPayload;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerReturnPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

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
                        REFERENCE.compatibleServer = true;
                    }
                }
        );
    }
}
