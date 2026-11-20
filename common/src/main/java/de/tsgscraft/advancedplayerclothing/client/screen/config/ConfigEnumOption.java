package de.tsgscraft.advancedplayerclothing.client.screen.config;

import de.tsgscraft.advancedplayerclothing.client.screen.ScreenUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ConfigEnumOption<T extends Enum<T> & ConfigEnum<T>> extends ConfigOption {

    private T value;
    private T initialValue;
    private final Consumer<T> onValueChanged;

    public ConfigEnumOption(int y, int screenWidth, Component name, Component description, ConfigScreen configScreen, T value) {
        this(y, screenWidth, name, description, configScreen, value, null);
    }

    public ConfigEnumOption(int y, int screenWidth, Component name, Component description, ConfigScreen configScreen, T value, Consumer<T> onValueChanged) {
        super(y, screenWidth, name, description, configScreen);
        this.value = value;
        this.initialValue = value;
        this.onValueChanged = onValueChanged;
    }

    @Override
    void renderOption(GuiGraphics guiGraphics, int x, int y, int width, int height, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(
                x,
                y,
                x + width,
                y + height,
                isOverButton(mouseX, mouseY) ? 0x44000000 : 0x77000000
        );

        Component name = value.getTranslation();

        if (isOverButton(mouseX, mouseY)) {
            name = name.copy().withStyle(ChatFormatting.UNDERLINE);
        }

        ScreenUtil.renderScrollingString(
                guiGraphics,
                Minecraft.getInstance().font,
                name,
                x + 2,
                y + 2,
                x + width - 2,
                y + height - 2,
                0xFFFFFFFF
        );
    }

    @Override
    void onButtonClick(int mouseX, int mouseY) {
        value = value.next();
        if (onValueChanged != null) {
            onValueChanged.accept(value);
        }
    }

    public T getValue() {
        return value;
    }

    @Override
    boolean isChanged() {
        return value != initialValue;
    }

    @Override
    void setInitialValue() {
        initialValue = value;
    }
}