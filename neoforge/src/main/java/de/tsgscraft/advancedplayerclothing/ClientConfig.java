package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.config.CustomPlayerModelEnum;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLED = BUILDER.comment(C_REFERENCE.ConfigComments.ENABLED).define("enabled", true);
    private static final ModConfigSpec.BooleanValue CUSTOM_SKIN = BUILDER.comment(C_REFERENCE.ConfigComments.CUSTOM_SKIN).define("customSkin", false);
    private static final ModConfigSpec.IntValue CUSTOM_PLAYER_MODEL = BUILDER.comment(C_REFERENCE.ConfigComments.CUSTOM_PLAYER_MODEL).defineInRange("customModel", 0, 0, 2);
    private static final ModConfigSpec.BooleanValue HIDE_DEFAULT = BUILDER.comment(C_REFERENCE.ConfigComments.HIDE_DEFAULT).define("hideDefault", false);
    // Debug options
    private static final ModConfigSpec.BooleanValue DEBUG = BUILDER.comment(C_REFERENCE.ConfigComments.DEBUG).define("debug.debug", false);
    private static final ModConfigSpec.BooleanValue DEBUG_SKIN = BUILDER.comment(C_REFERENCE.ConfigComments.DEBUG_SKIN).define("debug.debugSkin", false);
    private static final ModConfigSpec.BooleanValue DEBUG_SKIN_TEXTURE = BUILDER.comment(C_REFERENCE.ConfigComments.DEBUG_SKIN_TEXTURE).define("debug.debugSkinTexture", false);
    private static final ModConfigSpec.BooleanValue ONLY_BREASTS = BUILDER.comment(C_REFERENCE.ConfigComments.ONLY_BREASTS).define("debug.onlyBreasts", false);
    private static final ModConfigSpec.BooleanValue HIDE_LEFT_BOOB = BUILDER.comment(C_REFERENCE.ConfigComments.HIDE_LEFT_BOOB).define("debug.hideLeftBoob", false);
    private static final ModConfigSpec.BooleanValue HIDE_RIGHT_BOOB = BUILDER.comment(C_REFERENCE.ConfigComments.HIDE_RIGHT_BOOB).define("debug.hideRightBoob", false);
    private static final ModConfigSpec.BooleanValue DEBUG_FIRST_PERSON = BUILDER.comment(C_REFERENCE.ConfigComments.DEBUG_FIRST_PERSON).define("debug.debugFirstPerson", false);
    private static final ModConfigSpec.BooleanValue DEBUG_MODIFIERS = BUILDER.comment(C_REFERENCE.ConfigComments.DEBUG_MODIFIERS).define("debug.debugModifiers", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        ConfigValues.ENABLED = ENABLED.get();
        ConfigValues.CUSTOM_SKIN = CUSTOM_SKIN.get();
        ConfigValues.CUSTOM_PLAYER_MODEL = CustomPlayerModelEnum.fromID(CUSTOM_PLAYER_MODEL.get());
        ConfigValues.HIDE_DEFAULT = HIDE_DEFAULT.get();

        ConfigValues.DEBUG = DEBUG.get();
        ConfigValues.DEBUG_SKIN = DEBUG_SKIN.get();
        ConfigValues.DEBUG_SKIN_TEXTURE = DEBUG_SKIN_TEXTURE.get();
        ConfigValues.ONLY_BREASTS = ONLY_BREASTS.get();
        ConfigValues.HIDE_LEFT_BOOB = HIDE_LEFT_BOOB.get();
        ConfigValues.HIDE_RIGHT_BOOB = HIDE_RIGHT_BOOB.get();
        ConfigValues.DEBUG_FIRST_PERSON = DEBUG_FIRST_PERSON.get();
        ConfigValues.DEBUG_MODIFIERS = DEBUG_MODIFIERS.get();
    }

    public static void save() {
        ENABLED.set(ConfigValues.ENABLED);
        CUSTOM_SKIN.set(ConfigValues.CUSTOM_SKIN);
        CUSTOM_PLAYER_MODEL.set(ConfigValues.CUSTOM_PLAYER_MODEL.getId());
        HIDE_DEFAULT.set(ConfigValues.HIDE_DEFAULT);

        DEBUG.set(ConfigValues.DEBUG);
        DEBUG_SKIN.set(ConfigValues.DEBUG_SKIN);
        DEBUG_SKIN_TEXTURE.set(ConfigValues.DEBUG_SKIN_TEXTURE);
        ONLY_BREASTS.set(ConfigValues.ONLY_BREASTS);
        HIDE_LEFT_BOOB.set(ConfigValues.HIDE_LEFT_BOOB);
        HIDE_RIGHT_BOOB.set(ConfigValues.HIDE_RIGHT_BOOB);
        DEBUG_FIRST_PERSON.set(ConfigValues.DEBUG_FIRST_PERSON);
        DEBUG_MODIFIERS.set(ConfigValues.DEBUG_MODIFIERS);

        SPEC.save();
    }
}
