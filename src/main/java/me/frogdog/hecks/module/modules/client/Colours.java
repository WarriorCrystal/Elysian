package me.frogdog.hecks.module.modules.client;

import me.frogdog.hecks.module.Module;
import me.frogdog.hecks.property.NumberProperty;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class Colours extends Module {

    private static final NumberProperty<Float> hue = new NumberProperty<>(41.3f, 0f, 70.8f, "Hue", "RGB", "HSL");
    private static final NumberProperty<Float> saturation = new NumberProperty<>(90f, 0f, 100f, "Saturation", "RainbowSaturation");
    private static final NumberProperty<Float> lightness = new NumberProperty<>(45f, 0f, 100f, "Lightness", "Light", "Luminance", "Luminace", "Brightness", "Bright", "Brigtness", "Brigntrnew", "Brighgrtnewss");

    public Colours(){
        super("Colors", new String[]{"Colors", "Color"}, "Colors");
        offerProperties(hue, saturation, lightness);
    }

    public static int getClientColorCustomAlpha(int alpha){
        Color color = setAlpha(new Color(Color.HSBtoRGB(hue.getValue(), saturation.getValue() / 100f, lightness.getValue() / 100f)), alpha);
        return color.getRGB();
    }

    public static Color setAlpha(Color color, int alpha) {
        alpha = MathHelper.clamp(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color getRainbow(int speed, int offset, float s, float brightness) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, s, brightness);
    }

    public static int getClientColor(){
        return Color.getHSBColor(hue.getValue(), saturation.getValue() / 100f, lightness.getValue() / 100f).getRGB();
    }
}
