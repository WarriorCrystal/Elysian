package com.elysian.client.module.modules.render;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.module.ModuleType;

public class TimeChanger extends ToggleableModule {

    private final NumberProperty<Integer> time = new NumberProperty<Integer>(18000, 0, 24000, "Time");

    public TimeChanger() {
        super("TimeChanger", new String[] {"timechanger", "time"}, "Changes Time in the Client Side", ModuleType.RENDER);
        this.offerProperties(time, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.world == null) return;
        mc.world.setWorldTime(time.getValue());
    }
    
    @Override
    public void render(RenderWorldLastEvent event) {
        if (mc.world == null) return;
        mc.world.setWorldTime(time.getValue());
    }


}
