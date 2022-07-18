package com.elysian.client.module.modules.movement;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class Strafe extends ToggleableModule {

    int waitCounter;
    int forward;

    public Strafe() {
        super("Strafe", new String[]{"strafe"}, "Warrior Strafe, works on 2B2T", ModuleType.MOVEMENT);
        this.offerProperties(this.keybind);
        forward = 1;
    }

    @Override
    public void update(TickEvent event) {
        boolean boost;
        boolean bl = boost = Math.abs(mc.player.rotationYawHead - mc.player.rotationYaw) < 90.0f;
        if (mc.player.moveForward != 0.0f) {
            if (!mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
            float yaw = mc.player.rotationYaw;
            if (mc.player.moveForward > 0.0f) {
                if (mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += mc.player.movementInput.moveStrafe > 0.0f ? -45.0f : 45.0f;
                }
                this.forward = 1;
                mc.player.moveForward = 1.0f;
                mc.player.moveStrafing = 0.0f;
            } else if (mc.player.moveForward < 0.0f) {
                if (mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += mc.player.movementInput.moveStrafe > 0.0f ? 45.0f : -45.0f;
                }
                this.forward = -1;
                mc.player.moveForward = -1.0f;
                mc.player.moveStrafing = 0.0f;
            }
            if (mc.player.onGround) {
                mc.player.setJumping(false);
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                float f = (float)Math.toRadians(yaw);
                mc.player.motionY = 0.4;
                EntityPlayerSP player = mc.player;
                player.motionX -= (double)(MathHelper.sin((float)f) * 0.195f) * (double)this.forward;
                EntityPlayerSP player2 = mc.player;
                player2.motionZ += (double)(MathHelper.cos((float)f) * 0.195f) * (double)this.forward;
            }
        } else {
            double speed;
            if (this.waitCounter < 1) {
                ++this.waitCounter;
                return;
            }
            this.waitCounter = 0;
            double currentSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
            double d = speed = boost ? 1.0034 : 1.001;
            if (mc.player.motionY < 0.0) {
                speed = 1.0;
            }
            double yaw = 0.0;
            double direction = Math.toRadians(yaw);
            mc.player.motionX = -Math.sin(direction) * speed * currentSpeed * (double)this.forward;
            mc.player.motionZ = Math.cos(direction) * speed * currentSpeed * (double)this.forward;
        }
    }
}