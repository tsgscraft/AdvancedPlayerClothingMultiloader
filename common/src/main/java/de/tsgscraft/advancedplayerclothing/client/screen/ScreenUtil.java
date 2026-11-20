package de.tsgscraft.advancedplayerclothing.client.screen;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;

public class ScreenUtil {
    public static void renderScrollingString(GuiGraphics guiGraphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color) {
        renderScrollingString(guiGraphics, font, text, (minX + maxX) / 2, minX, minY, maxX, maxY, color);
    }

    public static void renderScrollingString(GuiGraphics guiGraphics, Font font, Component text, int centerX, int minX, int minY, int maxX, int maxY, int color) {
        int i = font.width(text);
        int j = (minY + maxY - 9) / 2 + 1;
        int k = maxX - minX;
        if (i > k) {
            int l = i - k;
            double d0 = (double) Util.getMillis() / (double)1000.0F;
            double d1 = Math.max((double)l * (double)0.5F, (double)3.0F);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / (double)2.0F + (double)0.5F;
            double d3 = Mth.lerp(d2, (double)0.0F, (double)l);
            guiGraphics.enableScissor(minX, minY, maxX, maxY);
            guiGraphics.drawString(font, text, minX - (int)d3, j, color);
            guiGraphics.disableScissor();
        } else {
            int i1 = Mth.clamp(centerX, minX + i / 2, maxX - i / 2);
            guiGraphics.drawCenteredString(font, text, i1, j, color);
        }
    }

    public static List<FormattedCharSequence> wrapText(Component description, int dWidth) {
        return Minecraft.getInstance().font.split(description, dWidth);
    }

    public enum TextType {
        YES_NO(             "gui.yes", "gui.no"),
        ON_OFF(             "options.on", "options.off"),
        ENABLED_DISABLED(   "addServer.resourcePack.enabled", "addServer.resourcePack.disabled"),
        RAW(         "", "");

        private final Component yes;
        private final Component no;

        TextType(String yes, String no) {
            if (yes.isEmpty() && no.isEmpty()) {
                this.yes = Component.literal("true");
                this.no = Component.literal("false");
            } else {
                this.yes = Component.translatable(yes);
                this.no = Component.translatable(no);
            }
        }

        public Component getText(boolean state) {
            return state ? yes : no;
        }

        public Component getYes() {
            return yes;
        }

        public Component getNo() {
            return no;
        }
    }
}
