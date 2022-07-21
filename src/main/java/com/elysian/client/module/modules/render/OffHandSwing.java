package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import net.minecraft.util.EnumHand;

public class OffHandSwing extends ToggleableModule {

    public OffHandSwing() {
        super("OffHandSwing", new String[] {"swing offhand"}, "sex", ModuleType.RENDER);
        this.offerProperties(this.keybind);
    }
    public void update() {

        mc.player.swingingHand = EnumHand.OFF_HAND;

    }
}
