package me.frogdog.hecks.module.modules.player;

import me.frogdog.hecks.event.events.PacketEvent;
import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class Sprint extends ToggleableModule {

    public Sprint() {
        super("Sprint", new String[] {"sprint", "togglesprint"}, "Sprints automatically", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (!(mc.player.isSprinting()) && !(mc.player.collidedHorizontally) && mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.player.setSprinting(true);
        }
    }
}
