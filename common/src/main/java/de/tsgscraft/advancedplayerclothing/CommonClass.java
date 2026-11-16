package de.tsgscraft.advancedplayerclothing;

public class CommonClass {

    public static ClothingRegistry clothingRegistry;

    private static boolean isClientSide = false;

    public static void init() {
        getClothingRegistry();
    }

    public static ClothingRegistry getClothingRegistry() {
        if (clothingRegistry == null) {
            clothingRegistry = new ClothingRegistry();
        }
        return clothingRegistry;
    }

    public static boolean isClientSide() {
        return isClientSide;
    }

    public static void setClientSide(boolean clientSide) {
        isClientSide = clientSide;
    }
}
