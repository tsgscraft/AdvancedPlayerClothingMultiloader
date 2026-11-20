package de.tsgscraft.advancedplayerclothing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.config.CustomPlayerModelEnum;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("advancedplayerclothing.json");

    public boolean enabled = true;
    public boolean customSkin = false;
    public int customModel = 0; // 0 = default, 1 = wide/steve, 2 = alex/slim
    public boolean hide_default = false;

    public Debug debug = new Debug();

    public static class Debug {
        public boolean debug = false;
        public boolean debugSkin = false;
        public boolean debugSkinTexture = false;
        public boolean onlyBreasts = false;
        public boolean hideLeftBoob = false;
        public boolean hideRightBoob = false;
        public boolean debugFirstPerson = false;
        public boolean debugModifiers = false;
    }

    public static void load() {
        ClientConfig cfg = new ClientConfig();
        if (Files.exists(FILE)) {
            try (Reader reader = Files.newBufferedReader(FILE)) {
                ClientConfig loaded = GSON.fromJson(reader, ClientConfig.class);
                if (loaded != null) cfg = loaded;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cfg.debug == null) cfg.debug = new Debug();
        cfg.customModel = Math.clamp(cfg.customModel, 0, 2);

        ConfigValues.ENABLED = cfg.enabled;
        ConfigValues.CUSTOM_SKIN = cfg.customSkin;
        ConfigValues.CUSTOM_PLAYER_MODEL = CustomPlayerModelEnum.fromID(cfg.customModel);
        ConfigValues.HIDE_DEFAULT = cfg.hide_default;

        ConfigValues.DEBUG = cfg.debug.debug;
        ConfigValues.DEBUG_SKIN = cfg.debug.debugSkin;
        ConfigValues.DEBUG_SKIN_TEXTURE = cfg.debug.debugSkinTexture;
        ConfigValues.ONLY_BREASTS = cfg.debug.onlyBreasts;
        ConfigValues.HIDE_LEFT_BOOB = cfg.debug.hideLeftBoob;
        ConfigValues.HIDE_RIGHT_BOOB = cfg.debug.hideRightBoob;
        ConfigValues.DEBUG_FIRST_PERSON = cfg.debug.debugFirstPerson;
        ConfigValues.DEBUG_MODIFIERS = cfg.debug.debugModifiers;

        try (Writer writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(cfg, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        ClientConfig cfg = new ClientConfig();
        cfg.enabled = ConfigValues.ENABLED;
        cfg.customSkin = ConfigValues.CUSTOM_SKIN;
        cfg.customModel = ConfigValues.CUSTOM_PLAYER_MODEL.getId();
        cfg.hide_default = ConfigValues.HIDE_DEFAULT;

        cfg.debug.debug = ConfigValues.DEBUG;
        cfg.debug.debugSkin = ConfigValues.DEBUG_SKIN;
        cfg.debug.debugSkinTexture = ConfigValues.DEBUG_SKIN_TEXTURE;
        cfg.debug.onlyBreasts = ConfigValues.ONLY_BREASTS;
        cfg.debug.hideLeftBoob = ConfigValues.HIDE_LEFT_BOOB;
        cfg.debug.hideRightBoob = ConfigValues.HIDE_RIGHT_BOOB;
        cfg.debug.debugFirstPerson = ConfigValues.DEBUG_FIRST_PERSON;
        cfg.debug.debugModifiers = ConfigValues.DEBUG_MODIFIERS;

        try (Writer writer = Files.newBufferedWriter(FILE)) {
            GSON.toJson(cfg, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
