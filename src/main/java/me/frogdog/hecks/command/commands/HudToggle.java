package me.frogdog.hecks.command.commands;

import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.command.Command;
import me.frogdog.hecks.property.Property;

public final class HudToggle extends Command {

    public HudToggle() {
        super(new String[] {"hudtoggle"}, "HudToggle (component)");
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        Property property = Hecks.getInstance().getModuleManager().getModuleByAlias("Hud Editor").getPropertyByAlias(args[0]);
        if (property.getValue() instanceof Boolean && property.getValue().equals(true)) {
            property.setValue(false);
        } else {
            property.setValue(true);
        }
    }
}
