package com.elysian.client.module.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import com.elysian.client.command.Command;
import com.elysian.client.event.events.InputEvent;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.mojang.realmsclient.gui.ChatFormatting;

public class StrengthDetect extends ToggleableModule {

    private final Set<EntityPlayer> str = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());

    public StrengthDetect() {
        super("StrengthDetect", new String[] {"Strengthnotif"}, "Notifies when someone has Strength", ModuleType.COMBAT);
        this.offerProperties(this.keybind);
    }
    
    @Override
    public void update(TickEvent event) {
        for (final EntityPlayer player : mc.world.playerEntities) {
            if (player.equals((Object)mc.player)) {
                continue;
            }
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                Command.sendClientSideMessage(ChatFormatting.RESET + player.getDisplayNameString() + " has " + ChatFormatting.RED + "strength");
                this.str.add(player);
            }
            if (!this.str.contains(player)) {
                continue;
            }
            if (player.isPotionActive(MobEffects.STRENGTH)) {
                continue;
            }
            Command.sendClientSideMessage(ChatFormatting.RESET + player.getDisplayNameString() + " no longer has " + ChatFormatting.RED + "strength");
            this.str.remove(player);
        }
    }

}
