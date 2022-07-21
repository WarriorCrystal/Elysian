package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OffHandSwing extends ToggleableModule {

    public OffHandSwing() {
        super("OffHandSwing", new String[] {"swing offhand"}, "sex", ModuleType.RENDER);
        this.offerProperties(this.keybind);
    }
    @Override
    public void update(TickEvent event) {

        mc.player.swingingHand = EnumHand.OFF_HAND;

    }
}
