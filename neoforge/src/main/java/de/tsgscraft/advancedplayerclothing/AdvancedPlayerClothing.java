package de.tsgscraft.advancedplayerclothing;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(REFERENCE.MOD_ID)
public class AdvancedPlayerClothing {

    // TODO: Better parsing of clothing models (some parts should be optional)
    // TODO: Better handling of error when loading a clothing model (currently it just crashes the game)

    public AdvancedPlayerClothing(IEventBus eventBus) {
        CommonClass.init();
    }
}
