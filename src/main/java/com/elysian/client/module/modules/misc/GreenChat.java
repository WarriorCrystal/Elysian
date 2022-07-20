package com.elysian.client.module.modules.misc;


import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.Property;

import net.minecraft.network.play.client.CPacketChatMessage;

import com.elysian.client.module.ModuleType;

public class GreenChat extends ToggleableModule {

    private final Property<Boolean> spaceToggle = new Property<Boolean>(true, "Space", "sp");

    public GreenChat() {
        super("GreenChat", new String[] {"ColorChat"}, "colors!!!!", ModuleType.MISC);
        this.offerProperties(spaceToggle, this.keybind);
    }

    @Override
    public void packet(PacketEvent event) {
        if(event.getPacket() instanceof CPacketChatMessage){
            if(((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith("-") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith("*") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(".") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith("&") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith("%") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith("$") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(",")) return;
            String message = ((CPacketChatMessage) event.getPacket()).getMessage();
            String space = "";
            if(spaceToggle.getValue()) space = " "; 
            if(!spaceToggle.getValue()) space = ""; 
            String finalM = ">" + space + message;
            if(finalM.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = finalM;
        }
    }

}
