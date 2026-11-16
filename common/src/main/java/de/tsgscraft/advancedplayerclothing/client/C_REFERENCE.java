package de.tsgscraft.advancedplayerclothing.client;

import de.tsgscraft.advancedplayerclothing.ConfigValues;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.screen.ClothingSelectionScreen;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class C_REFERENCE {
    // Main mod
    public static ResourceLocation debugSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player_debug.png");
    public static ResourceLocation customSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player.png");

    public static boolean isClientSlim = false;

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
