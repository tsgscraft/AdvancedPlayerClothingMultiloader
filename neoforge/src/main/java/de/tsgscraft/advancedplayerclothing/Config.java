package de.tsgscraft.advancedplayerclothing;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLED = BUILDER.comment("Whether the mod is enabled").define("enabled", true);
    private static final ModConfigSpec.BooleanValue CUSTOM_SKIN = BUILDER.comment("Whether to use a custom skin for the player (use a resource pack ('/textures/entity/player.png'))").define("customSkin", false);
    private static final ModConfigSpec.IntValue CUSTOM_PLAYER_MODEL = BUILDER.comment("Change you player model type (0 = default, 1 = wide/steve, 2 = alex/slim)").defineInRange("customModel", 0, 0, 2);

    // Debug options
    private static final ModConfigSpec.BooleanValue DEBUG = BUILDER.comment("Whether to enable debug mode").define("debug.debug", false);
    private static final ModConfigSpec.BooleanValue DEBUG_SKIN = BUILDER.comment("Whether to hide the base").define("debug.debugSkin", false);
    private static final ModConfigSpec.BooleanValue DEBUG_SKIN_TEXTURE = BUILDER.comment("Change the skin texture to a debug one").define("debug.debugSkinTexture", false);
    private static final ModConfigSpec.BooleanValue ONLY_BREASTS = BUILDER.comment("Hide everything but the Female Gender Mod Breast model").define("debug.onlyBreasts", false);
    private static final ModConfigSpec.BooleanValue HIDE_LEFT_BOOB = BUILDER.comment("Hide the left breast").define("debug.hideLeftBoob", false);
    private static final ModConfigSpec.BooleanValue HIDE_RIGHT_BOOB = BUILDER.comment("Hide the right breast").define("debug.hideRightBoob", false);
    private static final ModConfigSpec.BooleanValue DEBUG_FIRST_PERSON = BUILDER.comment("Whether to enable debug mode in first person").define("debug.debugFirstPerson", false);
    private static final ModConfigSpec.BooleanValue DEBUG_MODIFIERS = BUILDER.comment("Whether to enable debug mode for modifiers").define("debug.debugModifiers", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        ConfigValues.ENABLED = ENABLED.get();
        ConfigValues.CUSTOM_SKIN = CUSTOM_SKIN.get();
        ConfigValues.CUSTOM_PLAYER_MODEL = CUSTOM_PLAYER_MODEL.get();

        ConfigValues.DEBUG = DEBUG.get();
        ConfigValues.DEBUG_SKIN = DEBUG_SKIN.get();
        ConfigValues.DEBUG_SKIN_TEXTURE = DEBUG_SKIN_TEXTURE.get();
        ConfigValues.ONLY_BREASTS = ONLY_BREASTS.get();
        ConfigValues.HIDE_LEFT_BOOB = HIDE_LEFT_BOOB.get();
        ConfigValues.HIDE_RIGHT_BOOB = HIDE_RIGHT_BOOB.get();
        ConfigValues.DEBUG_FIRST_PERSON = DEBUG_FIRST_PERSON.get();
        ConfigValues.DEBUG_MODIFIERS = DEBUG_MODIFIERS.get();
    }
}
