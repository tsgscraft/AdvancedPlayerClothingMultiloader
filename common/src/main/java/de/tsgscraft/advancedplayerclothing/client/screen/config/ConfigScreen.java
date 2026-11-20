package de.tsgscraft.advancedplayerclothing.client.screen.config;

import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.config.CustomPlayerModelEnum;
import de.tsgscraft.advancedplayerclothing.client.screen.ScreenUtil;
import de.tsgscraft.advancedplayerclothing.client.screen.SimpleImageButton;
import de.tsgscraft.advancedplayerclothing.client.screen.SimpleTextButton;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {

    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Component.translatable("screen.advancedplayerclothing.config.title"));
        this.parent = parent;
    }

    public boolean initialized = false;

    public SimpleTextButton saveButton;
    public SimpleTextButton backButton;

    public ConfigBooleanOption enabledButton;
    public ConfigBooleanOption customSkinButton;
    public ConfigEnumOption<CustomPlayerModelEnum> customPlayerModelButton;
    public ConfigBooleanOption hideDefaultButton;

    public SimpleImageButton debugOptionsButton;
    public boolean showDebugOptions = false;

    public ConfigBooleanOption debugButton;
    public ConfigBooleanOption debugSkinButton;
    public ConfigBooleanOption debugSkinTextureButton;
    public ConfigBooleanOption debugOnlyBreastsButton;
    public ConfigBooleanOption debugHideLeftBoobButton;
    public ConfigBooleanOption debugHideRightBoobButton;
    public ConfigBooleanOption debugFirstPersonButton;
    public ConfigBooleanOption debugModifiersButton;

    @Override
    protected void init() {
        if (!initialized) {
            int y = 1;
            int constantSpacing = 24;
            int startY = height / 2 - 100;
            enabledButton = new ConfigBooleanOption(
                    startY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_ENABLED,
                    C_REFERENCE.ConfigComments.C_D_ENABLED,
                    this,
                    ConfigValues.ENABLED
            );
            y++;

            customSkinButton = new ConfigBooleanOption(
                    startY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_CUSTOM_SKIN,
                    C_REFERENCE.ConfigComments.C_D_CUSTOM_SKIN,
                    this,
                    ConfigValues.CUSTOM_SKIN
            );
            y++;

            customPlayerModelButton = new ConfigEnumOption<>(
                    startY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_CUSTOM_PLAYER_MODEL,
                    C_REFERENCE.ConfigComments.C_D_CUSTOM_PLAYER_MODEL,
                    this,
                    ConfigValues.CUSTOM_PLAYER_MODEL
            );
            y++;

            hideDefaultButton = new ConfigBooleanOption(
                    startY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_HIDE_DEFAULT,
                    C_REFERENCE.ConfigComments.C_D_HIDE_DEFAULT,
                    this,
                    ConfigValues.HIDE_DEFAULT
            );
            y++;

            saveButton = new SimpleTextButton(
                    width / 2 + 1,
                    startY + y * constantSpacing + 10,
                    100,
                    20,
                    Component.translatable("screen.advancedplayerclothing.config.save"),
                    () -> {
                        updateConfigValues();
                        Services.PLATFORM.saveClientConfig();
                    }
            );
            saveButton.setDisabled(true);

            backButton = new SimpleTextButton(
                    width / 2 - 101,
                    startY + y * constantSpacing + 10,
                    100,
                    20,
                    Component.translatable("screen.advancedplayerclothing.config.back"),
                    () -> {
                        Minecraft.getInstance().setScreen(parent);
                    }
            );

            int debugButtonSize = 20;
            debugOptionsButton = new SimpleImageButton(
                    width - debugButtonSize - 5,
                    5,
                    debugButtonSize,
                    debugButtonSize,
                    Component.translatable("screen.advancedplayerclothing.config.debug"),
                    C_REFERENCE.SPRITE_CONFIG,
                    () -> {
                        showDebugOptions = !showDebugOptions;
                        updateConfigOptions();
                    }
            );

            y = 0;
            int debugStartY = 30;
            debugButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_DEBUG,
                    C_REFERENCE.ConfigComments.C_D_DEBUG,
                    this,
                    ConfigValues.DEBUG
            );
            debugButton.setDebugOption();
            y++;
            debugSkinButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_DEBUG_SKIN,
                    C_REFERENCE.ConfigComments.C_D_DEBUG_SKIN,
                    this,
                    ConfigValues.DEBUG_SKIN
            );
            debugSkinButton.setDebugOption();
            y++;
            debugSkinTextureButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_DEBUG_SKIN_TEXTURE,
                    C_REFERENCE.ConfigComments.C_D_DEBUG_SKIN_TEXTURE,
                    this,
                    ConfigValues.DEBUG_SKIN_TEXTURE
            );
            debugSkinTextureButton.setDebugOption();
            y++;
            debugOnlyBreastsButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_ONLY_BREASTS,
                    C_REFERENCE.ConfigComments.C_D_ONLY_BREASTS,
                    this,
                    ConfigValues.ONLY_BREASTS
            );
            debugOnlyBreastsButton.setDebugOption();
            y++;
            debugHideLeftBoobButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_HIDE_LEFT_BOOB,
                    C_REFERENCE.ConfigComments.C_D_HIDE_LEFT_BOOB,
                    this,
                    ConfigValues.HIDE_LEFT_BOOB
            );
            debugHideLeftBoobButton.setDebugOption();
            y++;
            debugHideRightBoobButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_HIDE_RIGHT_BOOB,
                    C_REFERENCE.ConfigComments.C_D_HIDE_RIGHT_BOOB,
                    this,
                    ConfigValues.HIDE_RIGHT_BOOB
            );
            debugHideRightBoobButton.setDebugOption();
            y++;
            debugFirstPersonButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_DEBUG_FIRST_PERSON,
                    C_REFERENCE.ConfigComments.C_D_DEBUG_FIRST_PERSON,
                    this,
                    ConfigValues.DEBUG_FIRST_PERSON
            );
            debugFirstPersonButton.setDebugOption();
            y++;
            debugModifiersButton = new ConfigBooleanOption(
                    debugStartY + y * constantSpacing,
                    width,
                    C_REFERENCE.ConfigComments.C_DEBUG_MODIFIERS,
                    C_REFERENCE.ConfigComments.C_D_DEBUG_MODIFIERS,
                    this,
                    ConfigValues.DEBUG_MODIFIERS
            );
            debugModifiersButton.setDebugOption();
        }else {
            int y = 1;
            int constantSpacing = 24;
            int startY = height / 2 - 100;
            for (ConfigOption option : configOptions) {
                if (option.isDebugOption()) continue;
                option.resize(startY + y * constantSpacing, width);
                y++;
            }
            saveButton.resize(width / 2 + 1, startY + y * constantSpacing + 10, 100, 20);
            backButton.resize(width / 2 - 101, startY + y * constantSpacing + 10, 100, 20);
            y = 0;
            int debugStartY = 30;
            for (ConfigOption option : configOptions) {
                if (!option.isDebugOption()) continue;
                option.resize(debugStartY + y * constantSpacing, width);
                y++;
            }
            debugOptionsButton.resize(width - debugOptionsButton.getWidth() - 5, 5);
        }
        initialized = true;

        addRenderableWidget(saveButton);
        addRenderableWidget(backButton);

        addRenderableWidget(enabledButton);
        addRenderableWidget(customSkinButton);
        addRenderableWidget(customPlayerModelButton);
        addRenderableWidget(hideDefaultButton);

        addRenderableWidget(debugOptionsButton);

        updateConfigOptions();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        ScreenUtil.renderScrollingString(
                guiGraphics,
                Minecraft.getInstance().font,
                Component.translatable("screen.advancedplayerclothing.config.title").withStyle(style -> style.withUnderlined(true)),
                0,
                20,
                width,
                40,
                0xFFFFFFFF
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void updateConfigValues() {
        ConfigValues.ENABLED = enabledButton.getValue();
        ConfigValues.CUSTOM_SKIN = customSkinButton.getValue();
        ConfigValues.CUSTOM_PLAYER_MODEL = customPlayerModelButton.getValue();
        ConfigValues.HIDE_DEFAULT = hideDefaultButton.getValue();

        ConfigValues.DEBUG = debugButton.getValue();
        ConfigValues.DEBUG_SKIN = debugSkinButton.getValue();
        ConfigValues.DEBUG_SKIN_TEXTURE = debugSkinTextureButton.getValue();
        ConfigValues.ONLY_BREASTS = debugOnlyBreastsButton.getValue();
        ConfigValues.HIDE_LEFT_BOOB = debugHideLeftBoobButton.getValue();
        ConfigValues.HIDE_RIGHT_BOOB = debugHideRightBoobButton.getValue();
        ConfigValues.DEBUG_FIRST_PERSON = debugFirstPersonButton.getValue();
        ConfigValues.DEBUG_MODIFIERS = debugModifiersButton.getValue();

        for (ConfigOption option : configOptions) {
            option.setInitialValue();
        }
        saveButton.setDisabled(true);
    }

    private final List<ConfigOption> configOptions = new ArrayList<>();

    public void registerOption(ConfigOption configOption) {
        configOptions.add(configOption);
    }

    public void optionValueChanged() {
        boolean changed = false;
        for (ConfigOption option : configOptions) {
            if (option.isChanged()) {
                changed = true;
                break;
            }
        }
        saveButton.setDisabled(!changed);
    }

    public void updateConfigOptions() {
        for (ConfigOption option : configOptions) {
            if (option.isDebugOption()) {
                if (showDebugOptions) {
                    addRenderableWidget(option);
                } else {
                    removeWidget(option);
                }
            }
        }
    }
}
