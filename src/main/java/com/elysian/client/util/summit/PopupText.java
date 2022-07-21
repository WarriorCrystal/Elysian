package com.elysian.client.util.summit;

import net.minecraft.util.math.Vec3d;

public class PopupText
{
    private String displayName;
    private Vec3d position;
    private boolean markedToRemove;
    private int color;
    private Timer timer;
    private double yIncrease;
    public SalRainbowUtil rainbow = new SalRainbowUtil(9);
    
    public PopupText(final String displayName, final Vec3d pos) {
        timer = new Timer();
        yIncrease = 0.0;
        yIncrease = Math.random();
        while (yIncrease > 0.025 || yIncrease < 0.011) {
            yIncrease = Math.random();
        }
        timer.reset();
        setDisplayName(displayName);
        position = pos;
        markedToRemove = false;
        color = rainbow.GetRainbowColorAt(0);
    }
    
    public void Update() {
        position = position.add(0.0, yIncrease, 0.0);
        if (timer.passed(1000.0)) {
            markedToRemove = true;
        }
    }
    
    public Vec3d getPos() {
        return position;
    }
    
    public boolean isMarked() {
        return markedToRemove;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public int getColor() {
        return color;
    }
    
}
