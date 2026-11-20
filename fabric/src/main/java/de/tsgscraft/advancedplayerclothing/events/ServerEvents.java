package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.network.clothing.ClothingSyncPacket;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerEvents {
    public static void registerServerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Map<String, Map<String, String>> entries = new HashMap<>();

            for (Player player : server.getPlayerList().getPlayers()) {
                if (handler.getPlayer().getUUID().equals(player.getUUID())) continue;
                Map<String, String> a = ClothingRegistry.getInstance().getActiveClothingElements(player.getUUID());
                entries.put(
                        player.getUUID().toString(),
                        a
                );
            }

            Services.NETWORK.sendToPlayer(
                    handler.getPlayer(),
                    new ClothingSyncPacket(entries)
            );
        });
    }
}
