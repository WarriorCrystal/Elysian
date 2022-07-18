package com.elysian.client.module.modules.client;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

public final class ClickGui extends ToggleableModule {

    public ClickGui() {
        super("ClickGui", new String[]{"clickgui"}, "A standard clickgui", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.displayGuiScreen(com.elysian.client.ui.clickgui.ClickGui.getClickGui());
        this.setRunning(false);
    }
}
