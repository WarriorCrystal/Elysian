package com.elysian.client.module;

import com.elysian.client.util.interfaces.Labeled;

public enum ModuleType implements Labeled {
    COMBAT("Combat"),
    EXPLOIT("Exploit"),
    MISC("Misc"),
    PLAYER("Player"),
    RENDER("Render");

    private String label;

    private ModuleType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
