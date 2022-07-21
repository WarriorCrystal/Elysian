package com.elysian.client.module.modules.render;

import java.util.Random;

import com.elysian.client.util.spark.Timer;

import java.awt.*;

import net.minecraft.util.math.Vec3d;

class PopupText {
    private String displayName;
    public Vec3d pos;
    private boolean markedToRemove;
    public int color;
    private Timer timer;
    private double yIncrease;
    private final Random rand = new Random();

    public PopupText(final String displayName, final Vec3d pos) {
        this.timer = new Timer();
        this.yIncrease = Math.random();
        while (this.yIncrease > 0.025 || this.yIncrease < 0.011) {
            this.yIncrease = Math.random();
        }
        this.timer.reset();
        this.setDisplayName(displayName);
        this.pos = pos;
        this.markedToRemove = false;
        this.color = Color.getHSBColor(rand.nextFloat(), 1.0F, 0.9F).getRGB();
    }

    public void Update() {
        this.pos = this.pos.add(0.0, this.yIncrease, 0.0);
        if (this.timer.passedMs(1000)) {
            this.markedToRemove = true;
        }
    }

    public boolean isMarked() {
        return this.markedToRemove;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public int getColor() {
        return this.color;
    }
}
