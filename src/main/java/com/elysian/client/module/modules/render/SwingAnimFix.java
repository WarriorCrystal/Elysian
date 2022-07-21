package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SwingAnimFix extends ToggleableModule {

    public SwingAnimFix() {
        super("SwingAnimFix", new String[] {"fix swing animation"}, "yeah", ModuleType.RENDER);
        this.offerProperties(this.keybind);
    }
    @Override
    public void update(TickEvent event) {
        if (mc.player == null) {
            return;
        }
        if (mc.entityRenderer.itemRenderer.equippedProgressMainHand < 1.0f) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
        }
        if (mc.entityRenderer.itemRenderer.itemStackMainHand != mc.player.getHeldItemMainhand()) {
            mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
        }
    }
}
