package com.elysian.client.module.modules.movement;

import com.elysian.client.event.events.EventCancellable;
import com.elysian.client.event.events.EventEntity;
import com.elysian.client.event.events.EventPacket;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends ToggleableModule {

    public Velocity() {
        super("Velocity", new String[]{"Velocty"}, "Velocity", ModuleType.MOVEMENT);
        this.offerProperties(keybind);

        Listener<EventPacket.ReceivePacket> damage = new Listener<>(event -> {
            if (event.get_era() == EventCancellable.Era.EVENT_PRE) {
                if (event.get_packet() instanceof SPacketEntityVelocity) {
                    SPacketEntityVelocity knockback = (SPacketEntityVelocity) event.get_packet();

                    if (knockback.getEntityID() == mc.player.getEntityId()) {
                        event.cancel();

                        knockback.motionX *= 0.0f;
                        knockback.motionY *= 0.0f;
                        knockback.motionZ *= 0.0f;
                    }
                } else if (event.get_packet() instanceof SPacketExplosion) {
                    event.cancel();

                    SPacketExplosion knockback = (SPacketExplosion) event.get_packet();

                    knockback.motionX *= 0.0f;
                    knockback.motionY *= 0.0f;
                    knockback.motionZ *= 0.0f;
                }
            }
        });

        private Listener<EventEntity.WurstplusEventColision> explosion = new Listener<>(event -> {
            if (event.get_entity() == mc.player) {
                event.cancel();
        }
