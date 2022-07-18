package com.elysian.client.module.modules.movement;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class ReverseStep extends ToggleableModule {



    public ReverseStep() {
        super("ReverseStep", new String[]{"ReverseStep"}, "placeholder", ModuleType.MOVEMENT);
        this.offerProperties(this.keybind);
        }
    @Override
    public void update(TickEvent Tick) {
        if (mc.player == null || mc.world == null || mc.player.isInWater() || mc.player.isInLava()) {
            return;
        }
        if (mc.player.onGround) {
            --mc.player.motionY;
        }
    }
}