package de.tsgscraft.advancedplayerclothing.client.screen;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ClothingSelectionScreen extends Screen {

    private GridSelectionWidget<GridSelectionClothingType> gridSelectionTypeWidget;
    GridSelectionWidget<GridSelectionClothingElement> gridSelectionItemWidget;

    private SimpleImageButton backButton;
    private SimpleImageButton clearButton;
    private SimpleImageButton configButton;

    private String activeType = "";

    public ClothingSelectionScreen() {
        super(Component.literal("Clothing Selection"));
    }

    @Override
    protected void init() {
        this.gridSelectionTypeWidget = new GridSelectionWidget<>(this.width / 2 - 25, this.height / 2 - 100, 150, 200, 3, Component.literal("Clothing Selection"), this);
        this.gridSelectionItemWidget = new GridSelectionWidget<>(this.width / 2 - 25, this.height / 2 - 77, 150, 177, 3, Component.literal("Clothing Items"), this);
        List<String> type = ClothingRegistry.getInstance().getClothingTypes();
        List<GridSelectionClothingType> clothingTypes = type.stream().map(GridSelectionClothingType::new).toList();
        this.gridSelectionTypeWidget.setItems(clothingTypes);
        this.addRenderableWidget(this.gridSelectionTypeWidget);

        this.clearButton = new SimpleImageButton(this.width / 2 + 51, this.height / 2 - 100, 74, 20, Component.literal("Clear"), ResourceLocation.parse("advancedplayerclothing:none"), () -> {
            ClothingRegistry.getInstance().setClothing(activeType, "");
        });

        this.backButton = new SimpleImageButton(this.width / 2 - 25, this.height / 2 - 100, 73, 20, Component.literal("Back"), ResourceLocation.parse("advancedplayerclothing:back"), () -> {
            setActive("");
        });

        this.configButton = new SimpleImageButton(this.width / 2 + 128, this.height / 2 - 100, 20, 20, Component.literal("Config"), ResourceLocation.parse("advancedplayerclothing:config"), () -> {
            // TODO: Handle config button click
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
                InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x - 164, y - 80, x, y + 80, 45, 0, mouseX, mouseY, ent);
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
