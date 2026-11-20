package de.tsgscraft.advancedplayerclothing.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class SimpleTextButton extends AbstractWidget {

    private final Runnable onPress;

    private boolean disabled = false;

    public SimpleTextButton(int x, int y, int width, int height, Component message, Runnable onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fill(
                getX(),
                getY(),
                getX() + getWidth(),
                getY() + getHeight(),
                disabled ? 0x99000000 : isOverItem(mouseX, mouseY) ? 0x44000000 : 0x77000000
        );

        Component name = getMessage();

        if (isOverItem(mouseX, mouseY) && !disabled) {
            name = name.copy().withStyle(ChatFormatting.UNDERLINE);
        }

        ScreenUtil.renderScrollingString(
                guiGraphics,
                Minecraft.getInstance().font,
                name,
                getX() + 2,
                getY() + 2,
                getX() + getWidth() - 2,
                getY() + getHeight() - 2,
                disabled ? 0x77FFFFFF : 0xFFFFFFFF
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean flag = this.clicked(mouseX, mouseY);
                if (flag && !disabled) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY);
                    onPress.run();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOverItem(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void resize(int i, int i1, int i2, int i3) {
        setX(i);
        setY(i1);
        setWidth(i2);
        setHeight(i3);
    }
}
