package com.elysian.client.module.modules.movement;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.HoleUtils;
import net.minecraft.util.math.Vec3d;

import static com.elysian.client.util.PlayerUtil.getCenter;
import static com.elysian.client.util.PlayerUtil.getPlayerPos;

public class Anchor extends ToggleableModule {

    private final NumberProperty<Integer> height = new NumberProperty<Integer>(5, 0, 50, "Example");
    private final Property<Boolean> doubles  = new Property<Boolean>(true, "Example", "Example");
    private final Property<Boolean> drop  = new Property<Boolean>(true, "Example", "Example");
    private final NumberProperty<Double> speed  = new NumberProperty<Double>(5.0D, 0.0D, 50.0D, "Example");
    private final Property<Boolean> pitchD  = new Property<Boolean>(true, "Example", "Example");
    private final NumberProperty<Integer> pitch  = new NumberProperty<Integer>(5, 0, 50, "Example");

    public Anchor() {
        super("Anchor", new String[] {"Anchor"}, "Anchor", ModuleType.MOVEMENT);
        this.offerProperties(height, doubles, drop, speed, pitchD, pitch, keybind);
    }

    private Vec3d Center = Vec3d.ZERO;

    public void onMotionUpdate() {
        if (mc.world == null || mc.player == null || mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder()) {
            return;
        }

        for (int i = 0; i < height.getValue().intValue(); i++) {
            if (pitchD.getValue() && !(mc.player.rotationPitch >= pitch.getValue().intValue())) {
                return;
            }
            if (HoleUtils.isHole(getPlayerPos().down(i)) || (HoleUtils.isDoubleHole(getPlayerPos().down(i)) && doubles.getValue())) {
                Center = getCenter(mc.player.posX, mc.player.posY, mc.player.posZ);
                double XDiff = Math.abs(Center.x - mc.player.posX);
                double ZDiff = Math.abs(Center.z - mc.player.posZ);

                if (XDiff <= 0.1 && ZDiff <= 0.1) {
                    Center = Vec3d.ZERO;
                } else {
                    double MotionX = Center.x - mc.player.posX;
                    double MotionZ = Center.z - mc.player.posZ;

                    mc.player.motionX = MotionX / 2;
                    mc.player.motionZ = MotionZ / 2;
                    if(drop.getValue()) {
                        mc.player.motionY -= speed.getValue().floatValue();
                    }
                }
            }
        }
    }


}
