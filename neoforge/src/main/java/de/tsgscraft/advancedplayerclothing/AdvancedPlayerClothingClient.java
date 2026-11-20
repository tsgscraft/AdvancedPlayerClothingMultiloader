package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.client.anchor.Anchors;
import de.tsgscraft.advancedplayerclothing.client.anchor.anchors.*;
import de.tsgscraft.advancedplayerclothing.client.screen.config.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = REFERENCE.MOD_ID, dist = Dist.CLIENT)
public class AdvancedPlayerClothingClient {

    public AdvancedPlayerClothingClient(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.setClientSide(true);
        Anchors.registerAnchor(new HeadAnchor());
        Anchors.registerAnchor(new BodyAnchor());
        Anchors.registerAnchor(new LeftArmAnchor());
        Anchors.registerAnchor(new RightArmAnchor());
        Anchors.registerAnchor(new LeftLegAnchor());
        Anchors.registerAnchor(new RightLegAnchor());
        Anchors.registerAnchor(new LBoobAnchor());
        Anchors.registerAnchor(new RBoobAnchor());

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);

        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
                (container, modListScreen) -> new ConfigScreen(modListScreen));
    }
}
