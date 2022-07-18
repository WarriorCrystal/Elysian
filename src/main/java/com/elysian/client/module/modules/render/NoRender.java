package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.Property;

import net.minecraft.init.MobEffects;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public final class NoRender extends ToggleableModule {
    private final Property<Boolean> pumpkin = new Property<Boolean>(true, "NoPumpkin", "p", "np");
    private final Property<Boolean> fire = new Property<Boolean>(true, "NoFire", "fire", "nf");
    private final Property<Boolean> hurtcam = new Property<Boolean>(false, "NoHurtcam", "hurtcam", "nh");
    private final Property<Boolean> armour = new Property<Boolean>(false, "NoArmour", "armour",  "na");
    private final Property<Boolean> portalNausea = new Property<Boolean>(false, "NoPortalNausea", "portalnausea",  "np");

    public NoRender() {
        super("NoRender", new String[]{"render", "norender", "nr"}, "Stops annoying effects from being rendered", ModuleType.RENDER);
        this.offerProperties(this.pumpkin, this.fire, this.hurtcam, this.armour, this.portalNausea, this.keybind);
    }

    @Override
    public void render(RenderWorldLastEvent event) {
        if (NoRender.this.pumpkin.getValue()) {

        }

        if (NoRender.this.fire.getValue()) {

        }

        if (NoRender.this.hurtcam.getValue()) {

        }

        if (NoRender.this.portalNausea.getValue()) {
            GuiIngameForge.renderPortal = false;
            mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
        }

    }
}
