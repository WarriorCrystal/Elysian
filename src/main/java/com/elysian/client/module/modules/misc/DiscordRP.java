package com.elysian.client.module.modules.misc;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.util.RPC;

public class DiscordRP extends ToggleableModule {

    public DiscordRP() {
        super("RichPresence", new String[] {"DiscordRP"}, "Discord Presence", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        RPC.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        RPC.shutdown();
    }
}
