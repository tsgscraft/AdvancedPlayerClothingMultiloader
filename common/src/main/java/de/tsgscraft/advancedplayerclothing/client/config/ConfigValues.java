package de.tsgscraft.advancedplayerclothing.client.config;

public class ConfigValues {
    /*
    public static boolean enabled = true;
    public static boolean customSkin = false;
    public static int customModel = 0;

    public static boolean debug = false;
    public static boolean debugSkin = false;
    public static boolean debugSkinTexture = false;
    public static boolean onlyBreasts = false;
    public static boolean hideLeftBoob = false;
    public static boolean hideRightBoob = false;
    public static boolean debugFirstPerson = false;
    public static boolean debugModifiers = false;
     */
    public static boolean ENABLED = true;
    public static boolean CUSTOM_SKIN = false;
    public static CustomPlayerModelEnum CUSTOM_PLAYER_MODEL = CustomPlayerModelEnum.DEFAULT;
    public static boolean HIDE_DEFAULT = false; // TODO: Implement a way for the server to force this setting on the client side, so that the server can hide the default clothing for all players. (Maybe hide client side only clothing completely)

    public static boolean DEBUG = false;
    public static boolean DEBUG_SKIN = false;
    public static boolean DEBUG_SKIN_TEXTURE = false;
    public static boolean ONLY_BREASTS = false;
    public static boolean HIDE_LEFT_BOOB = false;
    public static boolean HIDE_RIGHT_BOOB = false;
    public static boolean DEBUG_FIRST_PERSON = false;
    public static boolean DEBUG_MODIFIERS = false;
}
