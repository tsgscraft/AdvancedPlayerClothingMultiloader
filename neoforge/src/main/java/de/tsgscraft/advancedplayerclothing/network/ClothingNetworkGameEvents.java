package de.tsgscraft.advancedplayerclothing.network;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.network.clothing.ClothingSyncPacket;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ClothingNetworkGameEvents {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer joiningPlayer))
            return;

        Map<String, Map<String, String>> entries = new HashMap<>();

        for (ServerPlayer player : joiningPlayer.server
                .getPlayerList().getPlayers()) {
            if (player.getUUID().toString().equals(joiningPlayer.getUUID().toString())) continue;
            Map<String, String> a = ClothingRegistry.getInstance().getActiveClothingElements(player.getUUID());
            entries.put(
                    player.getUUID().toString(),
                    a
            );
            System.out.println("Adding clothing data for player: " + player.getUUID().toString());
            for (Map.Entry<String, String> entry : a.entrySet()) {
                System.out.println("Clothing Type: " + entry.getKey() + " | Clothing Data: " + entry.getValue());
            }
        }

        Map<UUID, Map<String, String>> activeClothingElements = ClothingRegistry.getInstance().activeClothingElementsByPlayer;
        System.out.println("Active clothing elements: " + activeClothingElements);

        Services.NETWORK.sendToPlayer(
                joiningPlayer,
                new ClothingSyncPacket(entries)
        );

        System.out.println("Sent clothing sync to player: " + joiningPlayer.getUUID().toString() + " | Entries: " + entries);
    }
}
