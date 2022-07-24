package com.elysian.client.module.modules.movement;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class LongJump extends ToggleableModule {

    private final NumberProperty<Float> speed = new NumberProperty<Float>(30.00f, 1.00f, 100.00f, "speed");
    private final Property<Boolean> packet = new Property<Boolean>(false, "Packet", "LGPacket");
    private final Property<Boolean> toggle = new Property<Boolean>(false, "Toggle", "LGToggle");
    private static boolean jumped = false;
    private static boolean boostable = false;

    public LongJump() {
        super("LongJump", new String[] {"Salto largo"}, "Grego de mrd", ModuleType.MOVEMENT);
        this.offerProperties(speed, packet, toggle, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (LongJump.mc.player == null || LongJump.mc.world == null) {
            return;
        }
        if (jumped) {
            if (LongJump.mc.player.onGround || LongJump.mc.player.capabilities.isFlying) {
                jumped = false;
                LongJump.mc.player.motionX = 0.0;
                LongJump.mc.player.motionZ = 0.0;
                if (this.packet.getValue()) {
                    LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX, LongJump.mc.player.posY, LongJump.mc.player.posZ, LongJump.mc.player.onGround));
                    LongJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(LongJump.mc.player.posX + LongJump.mc.player.motionX, 0.0, LongJump.mc.player.posZ + LongJump.mc.player.motionZ, LongJump.mc.player.onGround));
                }
                if (this.toggle.getValue()) {
                    this.toggle();
                }
                return;
            }
            if (LongJump.mc.player.movementInput.moveForward == 0.0f && LongJump.mc.player.movementInput.moveStrafe == 0.0f) {
                return;
            }
            double yaw = this.getDirection();
            LongJump.mc.player.motionX = -Math.sin(yaw) * (double)((float)Math.sqrt(LongJump.mc.player.motionX * LongJump.mc.player.motionX + LongJump.mc.player.motionZ * LongJump.mc.player.motionZ) * (boostable ? (float)this.speed.getValue() / 10.0f : 1.0f));
            LongJump.mc.player.motionZ = Math.cos(yaw) * (double)((float)Math.sqrt(LongJump.mc.player.motionX * LongJump.mc.player.motionX + LongJump.mc.player.motionZ * LongJump.mc.player.motionZ) * (boostable ? (float)this.speed.getValue() / 10.0f : 1.0f));
            boostable = false;
            if (this.toggle.getValue()) {
                this.toggle();
            }
        }
        if (LongJump.mc.player.movementInput.moveForward == 0.0f && LongJump.mc.player.movementInput.moveStrafe == 0.0f && jumped) {
            LongJump.mc.player.motionX = 0.0;
            LongJump.mc.player.motionZ = 0.0;
        }
    }

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (LongJump.mc.player != null && LongJump.mc.world != null && event.getEntity() == LongJump.mc.player && (LongJump.mc.player.movementInput.moveForward != 0.0f || LongJump.mc.player.movementInput.moveStrafe != 0.0f)) {
            jumped = true;
            boostable = true;
        }
    }

    private double getDirection() {
        float rotationYaw = LongJump.mc.player.rotationYaw;
        if (LongJump.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (LongJump.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (LongJump.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (LongJump.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (LongJump.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

}
