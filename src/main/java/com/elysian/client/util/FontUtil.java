package com.elysian.client.util;

import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

import com.elysian.client.Elysian;

public class FontUtil {
    static FontRenderer fr = Elysian.getInstance().mc.fontRenderer;

    public static void drawString(String text, float x, float y, int color) {
        fr.drawString(text, x, y, color, true);
    }

    public static int getStringWidth(String text) {
        return fr.getStringWidth(text);
    }

    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}
