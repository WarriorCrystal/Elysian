package com.elysian.client.command.commands;

import com.elysian.client.command.Command;

public final class Help extends Command {

    public Help() {
        super(new String[] {"help"}, "Help");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("To see a list of all commands please use " + this.getPrefix() + "commands");
    }

}
