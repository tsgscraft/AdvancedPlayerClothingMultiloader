package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.events.Events;
import de.tsgscraft.advancedplayerclothing.network.NetworkEvents;
import net.fabricmc.api.ModInitializer;

public class Advancedplayerclothing implements ModInitializer {

    @Override
    public void onInitialize() {
        NetworkEvents.registerPayloadTypes();
        CommonClass.init();
    }
}
