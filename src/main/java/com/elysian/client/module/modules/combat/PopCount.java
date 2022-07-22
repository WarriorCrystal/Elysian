package com.elysian.client.module.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ConcurrentHashMap;

import com.elysian.client.command.Command;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.mojang.realmsclient.gui.ChatFormatting;

public class PopCount extends ToggleableModule {

    public ConcurrentHashMap<EntityPlayer, Integer> popMap = new ConcurrentHashMap<>();

    public PopCount() {
        super("PopCount", new String[] {"TotemPopCounter", "TotemPops"}, "Counts player's totem pops", ModuleType.COMBAT);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.getHealth() == 0.0f  && popMap.containsKey(player)) {
                Command.sendClientSideMessage(player.getName() + " died");
                popMap.remove(player);
            }
        }
    }

    @Override
    public void packet(PacketEvent event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) packet.getEntity(mc.world);
                int pops = popMap.getOrDefault(entity, 0) + 1;
                Command.sendClientSideMessage(entity.getName() + " popped " + ChatFormatting.RED + pops + ChatFormatting.RESET + " time/s");
                popMap.put(entity, pops);
            }
        }
    }
}
