package com.elysian.client.module.modules.movement;

import com.elysian.client.event.events.MoveEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.Property;
import com.elysian.client.util.EntityUtil;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Speed extends ToggleableModule {

    private final Property<Boolean> strafeJump = new Property<Boolean>(true, "Strafe Jump", "Strafe Jump");
    private final Property<Boolean> noShake = new Property<Boolean>(true, "No Shake", "No Shake");
    private final Property<Boolean> useTimer = new Property<Boolean>(true, "Use Timer", "Use Timer");
    private static Speed INSTANCE = new Speed();
    private double highChainVal = 0.0;
    private double lowChainVal = 0.0;
    private boolean oneTime = false;
    public double startY = 0.0;
    public boolean antiShake = false;
    private double bounceHeight = 0.4;
    private float move = 0.26f;
    public double minY = 0.0;
    public boolean changeY = false;
    private int vanillaCounter = 0;

    public Speed() {
        super("SpeedInstant", new String[]{"SpeedInstant"}, "SpeedInstant", ModuleType.MOVEMENT);
        this.offerProperties(strafeJump, noShake, useTimer, this.keybind);
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
            if (Speed.mc.player.onGround && this.strafeJump.getValue().booleanValue()) {
                Speed.mc.player.motionY = 0.4;
                event.setY(0.4);
            }
            MovementInput movementInput = Speed.mc.player.movementInput;
            float moveForward = movementInput.moveForward;
            float moveStrafe = movementInput.moveStrafe;
            float rotationYaw = Speed.mc.player.rotationYaw;
            if ((double)moveForward == 0.0 && (double)moveStrafe == 0.0) {
                event.setX(0.0);
                event.setZ(0.0);
            } else {
                if ((double)moveForward != 0.0) {
                    if ((double)moveStrafe > 0.0) {
                        rotationYaw += (float)((double)moveForward > 0.0 ? -45 : 45);
                    } else if ((double)moveStrafe < 0.0) {
                        rotationYaw += (float)((double)moveForward > 0.0 ? 45 : -45);
                    }
                    moveStrafe = 0.0f;
                    float f = moveForward == 0.0f ? moveForward : (moveForward = (double)moveForward > 0.0 ? 1.0f : -1.0f);
                }
                moveStrafe = moveStrafe == 0.0f ? moveStrafe : ((double)moveStrafe > 0.0 ? 1.0f : -1.0f);
                event.setX((double)moveForward * EntityUtil.getMaxSpeed() * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + (double)moveStrafe * EntityUtil.getMaxSpeed() * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
                event.setZ((double)moveForward * EntityUtil.getMaxSpeed() * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - (double)moveStrafe * EntityUtil.getMaxSpeed() * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
            }
        }
    }


