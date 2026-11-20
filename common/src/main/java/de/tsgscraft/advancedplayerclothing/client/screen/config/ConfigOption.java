package de.tsgscraft.advancedplayerclothing.client.screen.config;

import de.tsgscraft.advancedplayerclothing.client.screen.ScreenUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public abstract class ConfigOption extends AbstractWidget {

    private int BUTTON_WIDTH;
    private int BUTTON_HEIGHT;
    private int BUTTON_X;
    private int BUTTON_Y;

    private boolean debugOption = false;

    private static final int optionHeight = 20;

    private final ConfigScreen configScreen;

    private final Component description;

    public ConfigOption(int y, int screenWidth, Component name, Component description, ConfigScreen configScreen) {
        super(screenWidth/4, y, screenWidth/2, optionHeight, name);
        this.description = description;
        this.configScreen = configScreen;
        this.configScreen.registerOption(this);
        caclulateButtonPosition();
    }

    private void caclulateButtonPosition() {
        if (debugOption) {
            BUTTON_WIDTH = 30;
            BUTTON_HEIGHT = optionHeight;
            BUTTON_X = configScreen.width - BUTTON_WIDTH - 2;
            BUTTON_Y = getY();

        } else {
            BUTTON_WIDTH = (getWidth() / 3) - 4;
            BUTTON_HEIGHT = optionHeight;
            BUTTON_X = getX() + (getWidth() / 3) * 2 + 2;
            BUTTON_Y = getY();
        }
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ScreenUtil.renderScrollingString(
                guiGraphics,
                Minecraft.getInstance().font,
                getMessage(),
                getX() + 2,
                getY() + 2,
                getX() + ((getWidth() / 3) * 2) - 2,
                getY() + getHeight() - 2,
                0xFFFFFFFF
        );

        renderOption(guiGraphics, BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY, partialTick);

        // Render the description by the mouse hover if the mouse is over the option
        if (isOverItem(mouseX, mouseY)) {
            // In the foreground
            int d_width = 200;
            guiGraphics.renderTooltip(
                    Minecraft.getInstance().font,
                    ScreenUtil.wrapText(description, d_width),
                    mouseX,
                    mouseY
            );
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public boolean isOverItem(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    public boolean isOverButton(int mouseX, int mouseY) {
        return mouseX >= BUTTON_X && mouseX <= BUTTON_X + BUTTON_WIDTH && mouseY >= BUTTON_Y && mouseY <= BUTTON_Y + BUTTON_HEIGHT;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean flag = this.clicked(mouseX, mouseY);
                if (flag && isOverButton((int) mouseX, (int) mouseY)) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY);
                    onButtonClick((int) mouseX, (int) mouseY);
                    configScreen.optionValueChanged();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDebugOption() {
        return debugOption;
    }

    public void setDebugOption() {
        if (debugOption) return;
        this.debugOption = true;
        calcDebugOption();
        caclulateButtonPosition();
    }

    public void calcDebugOption() {
        if (!debugOption) return;
        setWidth(120);
        setX(configScreen.width - getWidth() - 2);
    }

    abstract void renderOption(GuiGraphics guiGraphics, int x, int y, int width, int height, int mouseX, int mouseY, float partialTick);
    abstract void onButtonClick(int mouseX, int mouseY);
    abstract boolean isChanged();
    abstract void setInitialValue();

    public void resize(int y, int screenWidth) {
        setY(y);
        setWidth(screenWidth / 2);
        setX(screenWidth / 4);
        calcDebugOption();
        caclulateButtonPosition();
    }
}
