package com.elysian.client.module.modules.player;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class AutoWalk extends ToggleableModule {

    public AutoWalk() {
        super("AutoWalk", new String[] {"AutoWalk", "autowalk"}, "Walks forward", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    }

}
