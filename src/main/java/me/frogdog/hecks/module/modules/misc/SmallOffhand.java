package me.frogdog.hecks.module.modules.misc;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.property.EnumProperty;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class SmallOffhand extends ToggleableModule {
    private final EnumProperty<Mode> mode = new EnumProperty<Mode> (Mode.Small, "Mode", "m");

    public SmallOffhand() {
        super("SmallOffhand", new String[] {"SmallOffhand", "smalloffhand"}, "Makes offhand item smaller", ModuleType.MISC);
        this.offerProperties(this.mode, this.keybind);

    }

    @Override
    public void update(TickEvent event) {
        if (SmallOffhand.this.mode.getValue().equals(Mode.Small)) {
            mc.entityRenderer.itemRenderer.equippedProgressOffHand = 0.5f;
        }

        if (SmallOffhand.this.mode.getValue().equals(Mode.Invis)) {
            mc.entityRenderer.itemRenderer.equippedProgressOffHand = -1.0f;
        }

    }

    public enum Mode {
        Small,
        Invis
    }
}
