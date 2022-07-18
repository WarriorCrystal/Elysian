package me.frogdog.hecks.module.modules.client;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;

public final class ClickGui extends ToggleableModule {

    public ClickGui() {
        super("ClickGui", new String[]{"clickgui"}, "A standard clickgui", ModuleType.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.displayGuiScreen(me.frogdog.hecks.ui.clickgui.ClickGui.getClickGui());
        this.setRunning(false);
    }
}
