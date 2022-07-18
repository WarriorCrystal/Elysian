package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import org.lwjgl.input.Keyboard;

public final class Bind extends Command {

    public Bind() {
        super(new String[] {"bind", "keybind"}, "bind (module) (key)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        String key = args[1].toUpperCase();
        Hecks.getInstance().getKeybindManager().getKeybindByLabel(args[0]).setKey(Keyboard.getKeyIndex(key));
        Command.sendClientSideMessage(args[0] + "'s bind has been set to " + key);
    }

}
