package com.elysian.client.module.modules.movement;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class Sprint extends ToggleableModule {

    public Sprint() {
        super("Sprint", new String[] {"sprint", "togglesprint"}, "Sprints automatically", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (!(mc.player.isSprinting()) && !(mc.player.collidedHorizontally) && mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.player.setSprinting(true);
        }
    }
}
