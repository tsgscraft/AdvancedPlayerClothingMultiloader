package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.CommonClass;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingSave;
import de.tsgscraft.advancedplayerclothing.client.simpleClothing.ClothingResourceLoader;
import de.tsgscraft.advancedplayerclothing.network.ClothingNetwork;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerPayload;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvents {

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ClothingResourceLoader());
    }

    private static int pendingTicks = -1;

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        try {
            Services.NETWORK.sendToServer(
                    new TestForServerPayload((byte) 0)
            );
        }catch (Exception ignored) {}
        if (CommonClass.isClientSide()) ClothingSave.loadActiveClothingData();
        ClothingNetwork.sendFullClothing();
        pendingTicks = 5;
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        REFERENCE.compatibleServer = false;
    }

    @SubscribeEvent
    public static void onTick(ClientTickEvent.Post event) {
        if (pendingTicks > 0 && --pendingTicks == 0) {
            ClothingNetwork.sendFullClothing();
        }
    }
}

