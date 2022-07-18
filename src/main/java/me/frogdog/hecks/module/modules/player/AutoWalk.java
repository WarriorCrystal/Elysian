package me.frogdog.hecks.module.modules.player;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class AutoWalk extends ToggleableModule {

    public AutoWalk() {
        super("AutoWalk", new String[] {"AutoWalk", "autowalk"}, "Walks forward", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    }

}
