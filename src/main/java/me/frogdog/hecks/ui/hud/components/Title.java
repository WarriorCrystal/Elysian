package me.frogdog.hecks.ui.hud.components;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.ui.hud.HudComponent;

public final class Title extends HudComponent {

    public Title() {
        super("Title", 2, 1);
    }

    @Override
    public Object getComponent() {
        return Hecks.getInstance().NAME + " " + Hecks.getInstance().VERSION;
    }
}
