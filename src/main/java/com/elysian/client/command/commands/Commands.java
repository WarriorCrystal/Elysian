package com.elysian.client.command.commands;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;

public final class Commands extends Command {

    public Commands() {
        super(new String[] {"commands, allcommands"}, "Commands");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Commands: ");
        Elysian.getInstance().getCommandManager().getRegistry().forEach(c -> {
            sendClientSideMessage(c.getSyntax());
        });
    }

}
