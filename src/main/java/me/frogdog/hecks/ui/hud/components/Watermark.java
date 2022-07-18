package me.frogdog.hecks.ui.hud.components;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.ui.hud.HudComponent;
import net.minecraft.util.ResourceLocation;

public final class Watermark extends HudComponent {

    private final ResourceLocation watermark = new ResourceLocation(Hecks.getInstance().MODID , "textures/watermark.png");

    public Watermark() {
        super("watermark", 10, 15);
    }

    @Override
    public ResourceLocation getComponent() {
        return watermark;
    }



}
