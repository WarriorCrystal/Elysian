package com.elysian.client.event.events;

import com.elysian.client.event.EventStage;
import com.elysian.client.util.Globals;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class PacketEventWurst extends EventStage implements Globals {
    private Packet<?> packet;
    public boolean hasSetPacket;

    public PacketEventWurst(int stage, Packet<?> packet) {
        super(stage);
        this.packet = packet;
        hasSetPacket = false;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) this.packet;
    }

    public void setPacket(Packet<?> packet){
        if(!nullCheck()){
            hasSetPacket = true;
            if(!this.isCancelled()){
                this.setCancelled(true);
            }
            mc.player.connection.sendPacket(packet);
        }
    }


    @Cancelable
    public static class Send extends PacketEventWurst {
        public Send(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }

    @Cancelable
    public static class Receive extends PacketEventWurst {
        public Receive(int stage, Packet<?> packet) {
            super(stage, packet);
        }
    }
}