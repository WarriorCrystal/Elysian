package me.frogdog.hecks.ui.hud.components;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.ui.hud.HudComponent;

public final class Coords extends HudComponent {

    public Coords() {
        super("coords", 2, 500);
    }

    @Override
    public String getComponent() {
        if (Hecks.getInstance().mc.player.dimension == 0) {
            return "X " + (long) Hecks.getInstance().mc.player.posX + " Y " + (long) Hecks.getInstance().mc.player.posY + " Z " +  (long) Hecks.getInstance().mc.player.posZ + " Nether: X " + (long) Hecks.getInstance().mc.player.posX / 8 + " Z " + (long) Hecks.getInstance().mc.player.posZ / 8;
        } else if (Hecks.getInstance().mc.player.dimension == -1) {
            return "X " + (long) Hecks.getInstance().mc.player.posX + " Y " + (long) Hecks.getInstance().mc.player.posY + " Z " +  (long) Hecks.getInstance().mc.player.posZ + " OverWorld: X " + (long) Hecks.getInstance().mc.player.posX * 8 + " Z " + (long) Hecks.getInstance().mc.player.posZ * 8;
        } else {
            return "X " + (long) Hecks.getInstance().mc.player.posX + " Y " + (long) Hecks.getInstance().mc.player.posY + " Z " +  (long) Hecks.getInstance().mc.player.posZ;
        }
    }

}
