package com.elysian.client.ui.hud.components;

import com.elysian.client.Elysian;
import com.elysian.client.ui.hud.HudComponent;

public final class Facing extends HudComponent {

    public Facing() {
        super("facing", 2, 490);
    }

    @Override
    public String getComponent() {
        return "Facing: " + Elysian.getInstance().mc.player.getHorizontalFacing().getName();
    }

}
