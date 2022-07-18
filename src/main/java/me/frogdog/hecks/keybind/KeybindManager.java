package me.frogdog.hecks.keybind;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.event.Event;
import me.frogdog.hecks.event.events.InputEvent;
import me.frogdog.hecks.module.Module;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.util.Registry;
import me.frogdog.hecks.util.interfaces.Toggleable;

import java.util.ArrayList;

public final class KeybindManager extends Registry<Keybind> {

    public KeybindManager() {
        this.registry = new ArrayList<Keybind>();

    }

    public void update(me.frogdog.hecks.event.events.InputEvent event) {
        if (event.getType() == InputEvent.Type.KEYBOARD_KEY_PRESS) {
            KeybindManager.this.registry.forEach(keybind -> {
                if (keybind.getKey() != 0 && keybind.getKey() == event.getKey()) {
                    String label = keybind.getLabel();
                    Module module = Hecks.getInstance().getModuleManager().getModuleByAlias(label);
                    if (!(module instanceof Toggleable)) {
                        Command.sendClientSideMessage("That module can't be toggled");
                    } else {
                        ToggleableModule toggleableModule = (ToggleableModule)module;
                        toggleableModule.toggle();
                        Command.sendClientSideMessage(module.getLabel() + " has been set to " + toggleableModule.isRunning());
                    }
                }
            });
        }
    }

    public Keybind getKeybindByLabel(String label) {
        for (Keybind keybind : registry) {
            if (!label.equalsIgnoreCase(keybind.getLabel())) continue;
            return keybind;
        }
        return null;
    }
}
