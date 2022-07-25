package com.elysian.client.module.modules.combat;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

public class MatadorModulinio extends ToggleableModule {

    public MatadorModulinio() {
        super("MatadorModulinio", new String[] {"MatadorModulinio"}, "MatadorModulinio", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }
    @Override
    public void onEnable() {
        mc.player.sendChatMessage("/kill");
        toggle();
    }
}
