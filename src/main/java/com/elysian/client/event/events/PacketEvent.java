//Decompiled by Procyon!

package com.elysian.client.event.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

public class PacketEvent extends Event
{
    Packet packet;
    
    public boolean isCancelable() {
        return true;
    }
    
    public PacketEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public static class Receive extends PacketEvent
    {
        public Receive(final Packet packet) {
            super(packet);
        }
    }
    
    public static class Send extends PacketEvent
    {
        public Send(final Packet packet) {
            super(packet);
        }
    }
}
