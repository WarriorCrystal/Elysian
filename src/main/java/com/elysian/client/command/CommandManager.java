package com.elysian.client.command;

import com.elysian.client.command.commands.*;
import com.elysian.client.util.Registry;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.util.ArrayList;

public class CommandManager extends Registry<Command> {

    private boolean found;

    public CommandManager() {
        this.registry = new ArrayList<Command>();

        register(new Help());
        register(new Commands());
        register(new Toggle());
        register(new HudToggle());
        register(new Bind());
        register(new AddFriend());
        register(new RemoveFriend());

    }

    public void callClientCommand(String input){
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String command = split[0];
        String args = input.substring(command.length()).trim();
        found = false;
        this.getRegistry().forEach(c ->{
            for(String s : c.getAlias()) {
                if (s.equalsIgnoreCase(command)) {
                    found = true;
                    try {
                        c.onClientCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    } catch (Exception e) {
                        Command.sendClientSideMessage(ChatFormatting.GREEN + c.getSyntax());
                    }
                }
            }
        });
        if (!found) {
            Command.sendClientSideMessage(ChatFormatting.GREEN + "Unknown command!");
        }

    }

}
