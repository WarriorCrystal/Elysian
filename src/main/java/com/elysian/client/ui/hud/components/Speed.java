package com.elysian.client.ui.hud.components;

import com.elysian.client.Elysian;
import com.elysian.client.ui.hud.HudComponent;
import com.elysian.client.util.Timer;

import net.minecraft.util.math.MathHelper;

public class Speed extends HudComponent {

    private final Timer timer = new Timer();

    public Speed() {
        super("speed", 2, 480);
    }

    @Override
    public String getComponent() {
        return "Speed: " + getSpeed() + "km/h";
    }

    public double getSpeed() {
        double speed;
        double prevPosX = 0;
        double prevPosZ = 0;
        if (timer.getPassedMillis(1000)) {
            prevPosX = Elysian.getInstance().mc.player.prevPosX;
            prevPosZ = Elysian.getInstance().mc.player.prevPosZ;
        }
        speed = Math.floor(((MathHelper.sqrt((Elysian.getInstance().mc.player.posX - prevPosX) * (Elysian.getInstance().mc.player.posX - prevPosX)+ (Elysian.getInstance().mc.player.posZ - prevPosZ) * (Elysian.getInstance().mc.player.posZ - prevPosZ))) / 1000.0f)/ (0.05f / 3600.0f));
        return speed;

    }

}
