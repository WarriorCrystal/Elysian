package com.elysian.client.module.modules.misc;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends ToggleableModule {

    public AutoReply() {
        super("AutoReply", new String[] {"AutoReply"}, "AutoReply", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }
    @EventHandler
    private Listener<ClientChatReceivedEvent> listener = new Listener<>(event ->{
        if( event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(mc.player.getName())){
            mc.player.sendChatMessage("/r Este es el AutoReply de Elysian, no me hables nob");
        }
    });
}
