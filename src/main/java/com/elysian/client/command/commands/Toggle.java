package com.elysian.client.command.commands;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.module.Module;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.util.interfaces.Toggleable;

public final class Toggle extends Command {

    public Toggle() {
        super(new String[] {"toggle"}, "Toggle (module)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Module module = Elysian.getInstance().getModuleManager().getModuleByAlias(args[0]);
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
