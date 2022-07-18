package me.frogdog.hecks.ui.hud.components;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.ui.hud.HudComponent;

public final class Facing extends HudComponent {

    public Facing() {
        super("facing", 2, 490);
    }

    @Override
    public String getComponent() {
        return "Facing: " + Hecks.getInstance().mc.player.getHorizontalFacing().getName();
    }

}
