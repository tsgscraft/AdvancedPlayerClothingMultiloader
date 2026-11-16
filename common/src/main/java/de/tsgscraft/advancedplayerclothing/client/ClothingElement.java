package de.tsgscraft.advancedplayerclothing.client;

import de.tsgscraft.advancedplayerclothing.client.modifiers.ClothingModifiers;
import de.tsgscraft.advancedplayerclothing.client.render.ClothingRendering;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ClothingElement(ClothingRendering renderInfo, ClothingModifiers modifiers, String name, String type, ResourceLocation id) {
    @Override
    public @NotNull String toString() {
        return id.toString();
    }

    public Component getTranslatedName() {
        return Component.translatable(name);
    }
}
