package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.module.Module;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.util.interfaces.Toggleable;

public final class Toggle extends Command {

    public Toggle() {
        super(new String[] {"toggle"}, "Toggle (module)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Module module = Hecks.getInstance().getModuleManager().getModuleByAlias(args[0]);
        if (module == null) {
            Command.sendClientSideMessage("No such module exists");
        } else if (!(module instanceof Toggleable)) {
            Command.sendClientSideMessage("That module can't be toggled");
        } else {
            ToggleableModule toggleableModule = (ToggleableModule)module;
            toggleableModule.toggle();
            Command.sendClientSideMessage(module.getLabel() + " has been set to " + toggleableModule.isRunning());
        }

    }

}
