package com.elysian.client.module.modules.misc;

import com.elysian.client.command.Command;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

public class GodModeBypass extends ToggleableModule {

    public GodModeBypass() {
        super("GodModeBypass", new String[] {"GodModeBypass"}, "GodModeBypass", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void packet(PacketEvent event) {
        if(event.getPacket() instanceof CPacketChatMessage){
            if(((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix())) return;
            String old = ((CPacketChatMessage) event.getPacket()).getMessage();
            String s = old.replace("r", "w").replace("R", "W").replace("ll", "ww").replace("LL", "WW") + " uwu";
            if(s.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    };
}
