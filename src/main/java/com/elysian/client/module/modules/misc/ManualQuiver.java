package com.elysian.client.module.modules.misc;

import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.module.ModuleType;

import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class ManualQuiver extends ToggleableModule {

    public ManualQuiver() {
        super("ManualQuiver", new String[] {"MQ", "Quiver"}, "Your arrows go up", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }
    
    @Override
    public void packet(PacketEvent event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.mc.player.rotationYaw, -90.0f, mc.player.onGround));
        }
            
    }

}
