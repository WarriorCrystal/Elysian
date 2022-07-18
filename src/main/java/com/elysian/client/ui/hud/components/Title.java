package com.elysian.client.ui.hud.components;

import com.elysian.client.Elysian;
import com.elysian.client.ui.hud.HudComponent;

public final class Title extends HudComponent {

    public Title() {
        super("Title", 2, 1);
    }

    @Override
    public Object getComponent() {
        return Elysian.getInstance().NAME + " " + Elysian.getInstance().VERSION;
    }
}
