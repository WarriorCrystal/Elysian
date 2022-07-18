package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public final class Fullbright extends ToggleableModule {
    private final EnumProperty<Mode> mode = new EnumProperty<Mode>(Mode.GAMMA, "Mode", "m");

    public Fullbright() {
        super("Fullbright", new String[]{"fullbright", "Fullbright", "fb"}, "Does exactly what the name indicates", ModuleType.RENDER);
        this.offerProperties(this.mode, this.keybind);
    }

    @Override
    public void render(RenderWorldLastEvent event) {
        if (Fullbright.this.mode.getValue() == Mode.GAMMA) {
            mc.gameSettings.gammaSetting = 1000f;
        }

        if(Fullbright.this.mode.getValue() == Mode.POTION) {
            mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 5, 0));
        }
    }

    public enum Mode {
        GAMMA,
        POTION
    }

}
