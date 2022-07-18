package com.elysian.client.module.modules.player;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class Strafe extends ToggleableModule {
    private final NumberProperty<Double> speed = new NumberProperty<Double>(0.5, 0.0, 3.0, "Speed");

    public Strafe() {
        super("Strafe", new String[]{"strafe"}, "Bhop with a fancy name", ModuleType.PLAYER);
        this.offerProperties(this.speed, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.onGround) {
            if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) {
                mc.player.jump();
                double[] direction = directionSpeed(speed.getValue());
                mc.player.motionX = direction[0];
                mc.player.motionZ = direction[1];

            }
        } else {
            double[] direction = directionSpeed(0.26);
            mc.player.motionX = direction[0];
            mc.player.motionZ = direction[1];
        }
    }

    private double[] directionSpeed(double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

        if (forward != 0) {
            if (side > 0) {
                yaw += (forward > 0 ? -45 : 45);
            } else if (side < 0) {
                yaw += (forward > 0 ? 45 : -45);
            }
            side = 0;

            if (forward > 0) {
                forward = 1;
            } else if (forward < 0) {
                forward = -1;
            }
        }

        final double sin = Math.sin(Math.toRadians(yaw + 90));
        final double cos = Math.cos(Math.toRadians(yaw + 90));
        final double posX = (forward * speed * cos + side * speed * sin);
        final double posZ = (forward * speed * sin - side * speed * cos);
        return new double[]{posX, posZ};
    }
}