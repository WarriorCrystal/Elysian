package com.elysian.client.ui.hud.components;

import com.elysian.client.Elysian;
import com.elysian.client.ui.hud.HudComponent;

public final class Coords extends HudComponent {

    public Coords() {
        super("coords", 2, 500);
    }

    @Override
    public String getComponent() {
        if (Elysian.getInstance().mc.player.dimension == 0) {
            return "X " + (long) Elysian.getInstance().mc.player.posX + " Y " + (long) Elysian.getInstance().mc.player.posY + " Z " +  (long) Elysian.getInstance().mc.player.posZ + " Nether: X " + (long) Elysian.getInstance().mc.player.posX / 8 + " Z " + (long) Elysian.getInstance().mc.player.posZ / 8;
        } else if (Elysian.getInstance().mc.player.dimension == -1) {
            return "X " + (long) Elysian.getInstance().mc.player.posX + " Y " + (long) Elysian.getInstance().mc.player.posY + " Z " +  (long) Elysian.getInstance().mc.player.posZ + " OverWorld: X " + (long) Elysian.getInstance().mc.player.posX * 8 + " Z " + (long) Elysian.getInstance().mc.player.posZ * 8;
        } else {
            return "X " + (long) Elysian.getInstance().mc.player.posX + " Y " + (long) Elysian.getInstance().mc.player.posY + " Z " +  (long) Elysian.getInstance().mc.player.posZ;
        }
    }

}
