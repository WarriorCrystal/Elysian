package com.elysian.client.module.modules.movement;

import com.elysian.client.module.Module;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

public final class Step extends ToggleableModule {

    private final Property<Boolean> vanilla = new Property<>(true, "Vanilla");
    private final NumberProperty<Float> height = new NumberProperty<>(2.1f, 1.0f, 10.0f, "Height");
    private final Property<Boolean> noliquid = new Property<>(true, "NoLiquid");
    private final Property<Boolean> turnoff = new Property<>(true, "AutoOff");


    public Step() {
        super("Step", new String[]{"Step"}, "Steping", ModuleType.MOVEMENT);
        this.offerProperties(this.vanilla, this.noliquid, this.turnoff, this.height);
        this.offerProperties(this.keybind);
        }
}
