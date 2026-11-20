package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.CommonClass;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingSave;
import de.tsgscraft.advancedplayerclothing.client.simpleClothing.ClothingResourceLoader;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerPayload;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvents {

    @SubscribeEvent
    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ClothingResourceLoader());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        try {
            Services.NETWORK.sendToServer(new TestForServerPayload((byte) 0));
        }catch (Exception ignored) {}
        if (CommonClass.isClientSide()) ClothingSave.loadActiveClothingData();
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        C_REFERENCE.compatibleServer = false;
        ClothingRegistry.getInstance().clearActiveClothingData(false);
    }
}

