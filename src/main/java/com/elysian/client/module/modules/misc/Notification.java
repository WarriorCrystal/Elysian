package com.elysian.client.module.modules.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

import com.elysian.client.command.Command;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.Property;

public final class Notification extends ToggleableModule {
    private final Property<Boolean> visualRange = new Property<Boolean>(false, "VisualRange", "vr");
    private final Property<Boolean> totemPop = new Property<Boolean>(true, "TotemPop", "tp");

    int delay = 0;
    int totalTotems;
    int totemCount;
    ArrayList<String> players = new ArrayList<String>();

    public Notification() {
        super("Notification", new String[] {"notifs", "Notification", "notification"}, "Send the player chat notifications", ModuleType.MISC);
        this.offerProperties(this.visualRange, this.totemPop, this.keybind);
    }

    @Override
    public void render(RenderWorldLastEvent event) {
        if (Notification.this.visualRange.getValue()) {
            for (Entity player : mc.world.loadedEntityList) {
                if (player instanceof EntityPlayer && player.getName() != mc.player.getName()) {
                    delay++;
                    if (!players.contains(player.getName())) {
                        if (delay > 10) {
                            Command.sendClientSideMessage(player.getName() + " has entered visual range");
                            players.add(player.getName());
                            delay = 0;
                        }
                    }

                    if(!isInRange(player.getName())) {
                        if(players.toString().contains(player.getName())) {
                            Command.sendClientSideMessage(player.getName() + " has left visual range");
                            players.remove(player.getName());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(TickEvent event) {
        if(Notification.this.totemPop.getValue()) {
            totemCount = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
            if(totemCount > totalTotems) {
                Command.sendClientSideMessage("You just popped a totem, you now have " + totemCount + " totems left");
                totalTotems = totemCount;
            } else {
                totalTotems = totemCount;
            }
        }
    }

    private boolean isInRange(String player) {
        return mc.world.playerEntities.toString().contains(player);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player != null) {
            totalTotems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        }
    }
}
