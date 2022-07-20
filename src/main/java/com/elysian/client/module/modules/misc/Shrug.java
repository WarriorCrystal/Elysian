package com.elysian.client.module.modules.misc;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

public class Shrug extends ToggleableModule {

    public Shrug() {
        super("Shurg", new String[] {"FacePro"}, ":VVVVVVVV", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void onEnable() {
        Shrug.mc.player.sendChatMessage(" \u00af\\_(\u30c4)_/\u00af");
        this.toggle();
    }
}
