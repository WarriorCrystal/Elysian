package com.elysian.client.command.commands;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.property.Property;

public final class HudToggle extends Command {

    public HudToggle() {
        super(new String[] {"hudtoggle"}, "HudToggle (component)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Property property = Elysian.getInstance().getModuleManager().getModuleByAlias("Hud Editor").getPropertyByAlias(args[0]);
        if (property.getValue() instanceof Boolean && property.getValue().equals(true)) {
            property.setValue(false);
        } else {
            property.setValue(true);
        }
    }
}
