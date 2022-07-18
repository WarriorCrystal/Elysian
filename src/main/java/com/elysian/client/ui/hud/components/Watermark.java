package com.elysian.client.ui.hud.components;

import com.elysian.client.Elysian;
import com.elysian.client.ui.hud.HudComponent;

import net.minecraft.util.ResourceLocation;

public final class Watermark extends HudComponent {

    private final ResourceLocation watermark = new ResourceLocation(Elysian.getInstance().MODID , "textures/LogoPng.png");

    public Watermark() {
        super("watermark", 10, 15);
    }

    @Override
    public ResourceLocation getComponent() {
        return watermark;
    }



}
