package com.elysian.client.keybind;

import java.util.ArrayList;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.event.events.InputEvent;
import com.elysian.client.module.Module;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.util.Registry;
import com.elysian.client.util.interfaces.Toggleable;
import com.mojang.realmsclient.gui.ChatFormatting;

public final class KeybindManager extends Registry<Keybind> {

    public KeybindManager() {
        this.registry = new ArrayList<Keybind>();

    }

    public void update(com.elysian.client.event.events.InputEvent event) {
        if (event.getType() == InputEvent.Type.KEYBOARD_KEY_PRESS) {
            KeybindManager.this.registry.forEach(keybind -> {
                if (keybind.getKey() != 0 && keybind.getKey() == event.getKey()) {
                    String label = keybind.getLabel();
                    Module module = Elysian.getInstance().getModuleManager().getModuleByAlias(label);
                    if (!(module instanceof Toggleable)) {
                        Command.sendClientSideMessage("That module can't be toggled");
                    } else {
                        ToggleableModule toggleableModule = (ToggleableModule)module;
                        toggleableModule.toggle();
                        if(toggleableModule.isRunning()) {
                            Command.sendClientSideMessage(module.getLabel() + " has been " + ChatFormatting.GREEN + "Enabled");
                        }
                        if(!toggleableModule.isRunning()) {
                            Command.sendClientSideMessage(module.getLabel() + " has been " + ChatFormatting.RED + "Disabled");
                        }
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
