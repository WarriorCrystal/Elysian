package com.elysian.client.module.modules.render;

import com.elysian.client.event.events.EventSetupFog;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class AntiFog extends ToggleableModule {

    public AntiFog() {
        super("AntiFog", new String[] {"AntiFog"}, "AntiFog", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @EventHandler
    private Listener<EventSetupFog> setup_fog = new Listener<>(event -> {

        event.cancel();

        mc.entityRenderer.setupFogColor(false);

        GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.colorMaterial(1028, 4608);

    });
}

