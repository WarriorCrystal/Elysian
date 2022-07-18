package me.frogdog.hecks.module;

import me.frogdog.hecks.util.interfaces.Labeled;

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
