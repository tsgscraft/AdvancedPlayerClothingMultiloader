package de.tsgscraft.advancedplayerclothing.client.screen.config;

import net.minecraft.network.chat.Component;

public interface ConfigEnum<T extends Enum<T> & ConfigEnum<T>> {
    Component getTranslation();
    int getId();
    T next();
}
