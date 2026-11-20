package de.tsgscraft.advancedplayerclothing.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SimpleBooleanButton extends AbstractWidget {

    private final static int ACTIVE = 0xFF27A300; // Green color for active state
    private final static int INACTIVE = 0xFFB30000; // Red color for inactive state

    private final Consumer<Boolean> onPress;

    private boolean state;
    private final ScreenUtil.TextType textType;

    public SimpleBooleanButton(int x, int y, int width, int height, boolean initialState, ScreenUtil.TextType textType, Component message, Consumer<Boolean> onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
        this.state = initialState;
        this.textType = textType;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(
                getX(),
                getY(),
                getX() + getWidth(),
                getY() + getHeight(),
                0x77000000
        );

        Component name = textType.getText(state);

        if (isOverItem(mouseX, mouseY, getX(), getY())) {
            name = name.copy().withStyle(ChatFormatting.UNDERLINE);
        }

        ScreenUtil.renderScrollingString(
                guiGraphics,
                Minecraft.getInstance().font,
                name,
                getX() + 2,
                getY() + 2,
                getWidth() - 2,
                getHeight() - 2,
                0xFFFFFFFF
        );
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        state = !state;
        onPress.accept(state);
        super.onClick(mouseX, mouseY);
    }

    private boolean isOverItem(int mouseX, int mouseY, int itemX, int itemY) {
        return mouseX >= itemX && mouseX <= itemX + getWidth() && mouseY >= itemY && mouseY <= itemY + getHeight();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
