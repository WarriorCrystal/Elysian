package com.elysian.client.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import com.elysian.client.Elysian;
import com.elysian.client.module.modules.client.Hud;
import com.elysian.client.ui.hud.components.*;
import com.elysian.client.util.Registry;

import static net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect;

public class HudManager extends Registry<HudComponent> {

    public HudManager() {
        this.registry = new ArrayList<HudComponent>();

        register(new Coords());
        register(new Title());
        register(new Facing());
        register(new Speed());
        register(new ModuleArray());
        register(new Watermark());

    }

    public Minecraft mc = Minecraft.getMinecraft();

    public void renderHud(RenderGameOverlayEvent event) {

        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRenderer;

        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            for (HudComponent h : this.registry) {
                if (Hud.getShow(h.getLabel())) {
                    if (h.getComponent() instanceof String) {
                        fr.drawStringWithShadow((String) h.getComponent(), h.getxPos(), h.getyPos(), rainbow(1 * 360));
                    } else if (h.getComponent() instanceof ArrayList) {
                        int y = 2;
                        for (String s : (Collection<? extends String>) h.getComponent()) {
                            fr.drawStringWithShadow(s, sr.getScaledWidth() - fr.getStringWidth(s) - 2, y, rainbow(1 * 360));
                            y += fr.FONT_HEIGHT;
                        }
                    } else if (h.getComponent() instanceof ResourceLocation) {
                        mc.renderEngine.bindTexture((ResourceLocation) h.getComponent());
                        drawScaledCustomSizeModalRect(h.getxPos(), h.getyPos(), 0, 0, 40, 40, 40, 40, 40, 40);
                    }
                }
            }
        }

    }

    public int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }


}
