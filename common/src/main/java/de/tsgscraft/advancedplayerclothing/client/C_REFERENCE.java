package de.tsgscraft.advancedplayerclothing.client;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.mixin.PlayerModelAccessor;
import de.tsgscraft.advancedplayerclothing.client.screen.clothing.ClothingSelectionScreen;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;

public class C_REFERENCE {
    // Main mod
    public static ResourceLocation debugSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player_debug.png");
    public static ResourceLocation customSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player.png");

    public static ResourceLocation SPRITE_NONE = ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "none");
    public static ResourceLocation SPRITE_BACK = ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "back");
    public static ResourceLocation SPRITE_CONFIG = ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "config");

    public static boolean isClientSlim() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            PlayerRenderer renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
            PlayerModelAccessor modelAccessor = (PlayerModelAccessor) renderer.getModel();
            return modelAccessor.isSlim();
        }
        return false;
    }

    public static boolean compatibleServer = false;

    public static final ModelLayerLocation ANCHOR_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "anchor_layer"), "main");

    public static final ModelLayerLocation SLIM_ARMOR_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "slim_armor_layer"), "main");

    public static final KeyMapping toggleEditGUI = new KeyMapping("key.advancedplayerclothing.clothing_selection_gui", GLFW.GLFW_KEY_U, "category.advancedplayerclothing.generic") {

        @Override
        public void setDown(boolean value) {
            if (value && !isDown()) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.screen == null && minecraft.player != null) {
                    minecraft.setScreen(new ClothingSelectionScreen());
                }
            }
            super.setDown(value);
        }
    };

    public static class ConfigComments {
        public static final String ENABLED = "Whether the mod is enabled";
        public static final Component C_ENABLED = Component.translatable("screen.advancedplayerclothing.config.enabled");
        public static final Component C_D_ENABLED = Component.translatable("screen.advancedplayerclothing.config.enabled.description");

        public static final String CUSTOM_SKIN = "Whether to use a custom skin for the player (use a resource pack ('/textures/entity/player.png'))";
        public static final Component C_CUSTOM_SKIN = Component.translatable("screen.advancedplayerclothing.config.custom_skin");
        public static final Component C_D_CUSTOM_SKIN = Component.translatable("screen.advancedplayerclothing.config.custom_skin.description");

        public static final String CUSTOM_PLAYER_MODEL = "Change you player model type (0 = default, 1 = wide/steve, 2 = alex/slim)";
        public static final Component C_CUSTOM_PLAYER_MODEL = Component.translatable("screen.advancedplayerclothing.config.custom_player_model");
        public static final Component C_D_CUSTOM_PLAYER_MODEL = Component.translatable("screen.advancedplayerclothing.config.custom_player_model.description");

        public static final String HIDE_DEFAULT = "Whether to hide the default prepackaged clothing";
        public static final Component C_HIDE_DEFAULT = Component.translatable("screen.advancedplayerclothing.config.hide_default");
        public static final Component C_D_HIDE_DEFAULT = Component.translatable("screen.advancedplayerclothing.config.hide_default.description");

        public static final String DEBUG = "Whether to enable debug mode";
        public static final Component C_DEBUG = Component.translatable("screen.advancedplayerclothing.config.debug.debug");
        public static final Component C_D_DEBUG = Component.translatable("screen.advancedplayerclothing.config.debug.debug.description");

        public static final String DEBUG_SKIN = "Whether to hide the base";
        public static final Component C_DEBUG_SKIN = Component.translatable("screen.advancedplayerclothing.config.debug.debug_skin");
        public static final Component C_D_DEBUG_SKIN = Component.translatable("screen.advancedplayerclothing.config.debug.debug_skin.description");

        public static final String DEBUG_SKIN_TEXTURE = "Change the skin texture to a debug one";
        public static final Component C_DEBUG_SKIN_TEXTURE = Component.translatable("screen.advancedplayerclothing.config.debug.debug_skin_texture");
        public static final Component C_D_DEBUG_SKIN_TEXTURE = Component.translatable("screen.advancedplayerclothing.config.debug.debug_skin_texture.description");

        public static final String ONLY_BREASTS = "Hide everything but the Female Gender Mod Breast model";
        public static final Component C_ONLY_BREASTS = Component.translatable("screen.advancedplayerclothing.config.debug.only_breasts");
        public static final Component C_D_ONLY_BREASTS = Component.translatable("screen.advancedplayerclothing.config.debug.only_breasts.description");

        public static final String HIDE_LEFT_BOOB = "Hide the left breast";
        public static final Component C_HIDE_LEFT_BOOB = Component.translatable("screen.advancedplayerclothing.config.debug.hide_left_boob");
        public static final Component C_D_HIDE_LEFT_BOOB = Component.translatable("screen.advancedplayerclothing.config.debug.hide_left_boob.description");

        public static final String HIDE_RIGHT_BOOB = "Hide the right breast";
        public static final Component C_HIDE_RIGHT_BOOB = Component.translatable("screen.advancedplayerclothing.config.debug.hide_right_boob");
        public static final Component C_D_HIDE_RIGHT_BOOB = Component.translatable("screen.advancedplayerclothing.config.debug.hide_right_boob.description");

        public static final String DEBUG_FIRST_PERSON = "Whether to enable debug mode in first person";
        public static final Component C_DEBUG_FIRST_PERSON = Component.translatable("screen.advancedplayerclothing.config.debug.debug_first_person");
        public static final Component C_D_DEBUG_FIRST_PERSON = Component.translatable("screen.advancedplayerclothing.config.debug.debug_first_person.description");

        public static final String DEBUG_MODIFIERS = "Whether to enable debug mode for modifiers";
        public static final Component C_DEBUG_MODIFIERS = Component.translatable("screen.advancedplayerclothing.config.debug.debug_modifiers");
        public static final Component C_D_DEBUG_MODIFIERS = Component.translatable("screen.advancedplayerclothing.config.debug.debug_modifiers.description");
    }

    public static List<String> defaultClothing = List.of(
            "advancedplayerclothing:clothing/hats/tophat.json"
    );

    public static ResourceLocation playerSkinIdentifier = ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "player_skin");

    public static ResourceLocation getSkinTexture(AbstractClientPlayer player, ResourceLocation textureId, ResourceLocation debugTextureId) {
        ResourceLocation texture;
        if (ConfigValues.DEBUG && debugTextureId != null) {
            texture = textureId == playerSkinIdentifier ? debugSkin : debugTextureId;
        } else {
            texture = textureId == playerSkinIdentifier ? getSkinTexture(player) : textureId;
        }
        return texture;
    }

    public static ResourceLocation getSkinTexture(AbstractClientPlayer player) {
        return (ConfigValues.DEBUG && ConfigValues.DEBUG_SKIN_TEXTURE) ? debugSkin : (ConfigValues.CUSTOM_SKIN && isCurrentPlayer(player.getUUID())) ? customSkin : player.getSkin().texture();
    }



    // First Person Mod (firstperson)
    public static boolean skipHead = false;

    public static void updateFirstPerson() {
        skipHead = false;
        LocalPlayer player = Minecraft.getInstance().player;

        if (Services.PLATFORM.isModLoaded(REFERENCE.fp_MODID)) {
            skipHead = Services.COMPAT.shouldSkinHead();
            if (ConfigValues.DEBUG_FIRST_PERSON)
                player.sendSystemMessage(Component.literal("First Person Mod detected. Skip head: " + skipHead));
        }else if (ConfigValues.DEBUG_FIRST_PERSON)
            player.sendSystemMessage(Component.literal("First Person Mod not found."));

    }

    // Utility method to check if the given UUID is the current player's UUID
    public static boolean isCurrentPlayer(UUID uuid) {
        return uuid.equals(getClientPlayerUUID());
    }

    public static UUID getClientPlayerUUID() {
        if (Minecraft.getInstance().player == null) {
            return null;
        }
        return Minecraft.getInstance().player.getUUID();
    }
}
