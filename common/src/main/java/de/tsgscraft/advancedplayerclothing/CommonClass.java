package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.client.screen.ClothingSelectionScreen;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class CommonClass {

    public static ClothingRegistry clothingRegistry;

    private static boolean isClientSide = false;

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

    public static void init() {
        getClothingRegistry();
    }

    public static ClothingRegistry getClothingRegistry() {
        if (clothingRegistry == null) {
            clothingRegistry = new ClothingRegistry();
        }
        return clothingRegistry;
    }

    public static UUID getClientPlayerUUID() {
        if (Minecraft.getInstance().player == null) {
            return null;
        }
        return Minecraft.getInstance().player.getUUID();
    }

    public static boolean isClientSide() {
        return isClientSide;
    }

    public static void setClientSide(boolean clientSide) {
        isClientSide = clientSide;
    }
}
