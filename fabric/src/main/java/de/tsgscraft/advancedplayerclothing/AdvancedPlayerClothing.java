package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.network.NetworkEvents;
import net.fabricmc.api.ModInitializer;

public class AdvancedPlayerClothing implements ModInitializer {

    @Override
    public void onInitialize() {
        NetworkEvents.registerPayloadTypes();
        NetworkEvents.registerServerReceivers();
        CommonClass.init();
    }
}
