package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.client.anchor.Anchors;
import de.tsgscraft.advancedplayerclothing.client.anchor.anchors.*;
import de.tsgscraft.advancedplayerclothing.events.Events;
import de.tsgscraft.advancedplayerclothing.network.NetworkEvents;
import net.fabricmc.api.ClientModInitializer;

public class AdvancedPlayerClothingClient implements ClientModInitializer {

    // TODO: Config Screen
    // TODO: Fix icon for mod menu

    @Override
    public void onInitializeClient() {
        CommonClass.setClientSide(true);
        Anchors.registerAnchor(new HeadAnchor());
        Anchors.registerAnchor(new BodyAnchor());
        Anchors.registerAnchor(new LeftArmAnchor());
        Anchors.registerAnchor(new RightArmAnchor());
        Anchors.registerAnchor(new LeftLegAnchor());
        Anchors.registerAnchor(new RightLegAnchor());
        Anchors.registerAnchor(new LBoobAnchor());
        Anchors.registerAnchor(new RBoobAnchor());

        Config.load();

        Events.registerClientEvents();

        NetworkEvents.registerClientReceivers();
    }
}
