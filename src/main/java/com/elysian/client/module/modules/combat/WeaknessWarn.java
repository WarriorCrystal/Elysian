package com.elysian.client.module.modules.combat;

import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.elysian.client.command.Command;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.mojang.realmsclient.gui.ChatFormatting;

public class WeaknessWarn extends ToggleableModule {

    private boolean hasAnnounced = false;

    public WeaknessWarn() {
        super("WeaknessWarn", new String[] {"WeaknessAnnounce"}, "Warns you when you get Weakness", ModuleType.COMBAT);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
            hasAnnounced = true;
            Command.sendClientSideMessage(ChatFormatting.RESET + "you've got " + ChatFormatting.GRAY + "weakness");
        }
        if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
            hasAnnounced = false;
            Command.sendClientSideMessage(ChatFormatting.RESET + "you no longer have " + ChatFormatting.GRAY + "weakness");
        }
    }

}
