package com.elysian.client.module.modules.misc;

import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.Property;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

public class ChatSuffix extends ToggleableModule {

    private final Property<Boolean> commands  = new Property<Boolean>(true, "Commands", "Commands");

    private final String ELYSIAN = " \u23D0 \u0364\u006C\u0079\u0073\u0061\u006E";

    public ChatSuffix() {
        super("ChatSuffix", new String[] {"ChatSuffix"}, "ChatSuffix", ModuleType.MISC);
        this.offerProperties(commands, this.keybind);
    }

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            if (s.startsWith("/") && !commands.getValue()) return;
            s += ELYSIAN;
            if (s.length() >= 256) s = s.substring(0,256);
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });
}
