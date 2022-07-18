package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;

public final class Commands extends Command {

    public Commands() {
        super(new String[] {"commands, allcommands"}, "Commands");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Command.sendClientSideMessage("Commands: ");
        Hecks.getInstance().getCommandManager().getRegistry().forEach(c -> {
            sendClientSideMessage(c.getSyntax());
        });
    }

}
