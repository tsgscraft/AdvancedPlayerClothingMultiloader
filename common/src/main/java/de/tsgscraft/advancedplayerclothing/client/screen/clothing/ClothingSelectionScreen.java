package de.tsgscraft.advancedplayerclothing.client.screen.clothing;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.screen.GridSelectionWidget;
import de.tsgscraft.advancedplayerclothing.client.screen.SimpleImageButton;
import de.tsgscraft.advancedplayerclothing.client.screen.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ClothingSelectionScreen extends Screen {

    private GridSelectionWidget<GridSelectionClothingType> gridSelectionTypeWidget;
    GridSelectionWidget<GridSelectionClothingElement> gridSelectionItemWidget;

    private SimpleImageButton backButton;
    private SimpleImageButton clearButton;
    private SimpleImageButton configButton;

    private String activeType = "";

    public ClothingSelectionScreen() {
        super(Component.translatable("screen.advancedplayerclothing.clothing.title"));
    }

    @Override
    protected void init() {
        this.gridSelectionTypeWidget = new GridSelectionWidget<>(this.width / 2 - 25, this.height / 2 - 100, 150, 200, 3, Component.literal("Clothing Selection"), this);
        this.gridSelectionItemWidget = new GridSelectionWidget<>(this.width / 2 - 25, this.height / 2 - 77, 150, 177, 3, Component.literal("Clothing Items"), this);
        List<String> type = new ArrayList<>(ClothingRegistry.getInstance().getClothingTypes());
        if (ConfigValues.HIDE_DEFAULT) {
            List<String> defaultClothing = C_REFERENCE.defaultClothing;
            ClothingRegistry.getInstance().getClothingTypeToIdMap().forEach((clothingType, clothingIds) -> {
                List<String> ids = new ArrayList<>(clothingIds);
                ids.removeIf(defaultClothing::contains);
                if (ids.isEmpty()) {
                    type.remove(clothingType);
                }
            });
        }
        List<GridSelectionClothingType> clothingTypes = type.stream().map(GridSelectionClothingType::new).toList();
        this.gridSelectionTypeWidget.setItems(clothingTypes);
        this.addRenderableWidget(this.gridSelectionTypeWidget);

        this.clearButton = new SimpleImageButton(this.width / 2 + 51, this.height / 2 - 100, 74, 20, Component.literal("Clear"), C_REFERENCE.SPRITE_NONE, () -> {
            ClothingRegistry.getInstance().setClothing(activeType, "");
        });

        this.backButton = new SimpleImageButton(this.width / 2 - 25, this.height / 2 - 100, 73, 20, Component.literal("Back"), C_REFERENCE.SPRITE_BACK, () -> {
            setActive("");
        });

        this.configButton = new SimpleImageButton(this.width / 2 + 128, this.height / 2 - 100, 20, 20, Component.literal("Config"), C_REFERENCE.SPRITE_CONFIG, () -> {
            Minecraft.getInstance().setScreen(new ConfigScreen(this));
        });
        this.addRenderableWidget(this.configButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int x = this.width / 2;
        int y = this.height / 2;

        if (minecraft != null && minecraft.level != null) {
            Player ent = minecraft.level.getPlayerByUUID(minecraft.player.getUUID());
            if (ent != null) {
                boolean oldSkipHead = C_REFERENCE.skipHead;
                C_REFERENCE.skipHead = false;
                InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x - 164, y - 80, x, y + 80, 45, 0, mouseX, mouseY, ent);
                C_REFERENCE.skipHead = oldSkipHead;
            }
        }
    }

    public void setActive(String type) {
        if (type.isBlank()) {
            this.activeType = "";
            addRenderableWidget(this.gridSelectionTypeWidget);
            removeWidget(this.gridSelectionItemWidget);
            removeWidget(this.clearButton);
            removeWidget(this.backButton);
            gridSelectionTypeWidget.sort();
        }else {
            this.activeType = type;
            addRenderableWidget(this.gridSelectionItemWidget);
            addRenderableWidget(this.clearButton);
            addRenderableWidget(this.backButton);
            removeWidget(this.gridSelectionTypeWidget);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
