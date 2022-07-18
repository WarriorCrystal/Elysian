package com.elysian.client.ui.hud.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.elysian.client.Elysian;
import com.elysian.client.module.Module;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.ui.hud.HudComponent;
import com.elysian.client.util.FontUtil;

public final class ModuleArray extends HudComponent {

    private ArrayList<String> moduleArray = new ArrayList<String>();

    public ModuleArray() {
        super("moduleArray", 0, 0);
    }

    public static class ModuleComparator implements Comparator<String> {

        @Override
        public int compare(String m1, String m2) {
            if (FontUtil.getStringWidth(m1) > FontUtil.getStringWidth(m2)) {
                return -1;
            }
            if (FontUtil.getStringWidth(m1) > FontUtil.getStringWidth(m2)) {
                return 1;
            }
            return 0;
        }

    }


    @Override
    public ArrayList<String> getComponent() {
        moduleArray.clear();
        for (Module m : Elysian.getInstance().getModuleManager().getRegistry()) {
            if (m instanceof ToggleableModule) {
                ToggleableModule toggleableModule = (ToggleableModule)m;
                if (toggleableModule.isRunning()) {
                    moduleArray.add(m.getLabel());
                }
            }
        }

        Collections.sort(moduleArray, new ModuleComparator());
        return moduleArray;
    }

}
