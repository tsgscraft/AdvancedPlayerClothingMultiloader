package de.tsgscraft.advancedplayerclothing.client.config;

import de.tsgscraft.advancedplayerclothing.client.screen.config.ConfigEnum;
import net.minecraft.network.chat.Component;

public enum CustomPlayerModelEnum implements ConfigEnum<CustomPlayerModelEnum> {
    DEFAULT(0, "advancedplayerclothing.config.player_model.default"),
    WIDE(1, "advancedplayerclothing.config.player_model.wide"),
    SLIM(2, "advancedplayerclothing.config.player_model.slim");

    private final int id;
    private final Component translation;

    CustomPlayerModelEnum(int id, String translation) {
        this.id = id;
        this.translation = Component.translatable(translation);
    }

    public static CustomPlayerModelEnum fromID(int customModel) {
        for (CustomPlayerModelEnum model : values()) {
            if (model.id == customModel) {
                return model;
            }
        }
        return DEFAULT;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public CustomPlayerModelEnum next() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }

    @Override
    public Component getTranslation() {
        return translation;
    }
}
