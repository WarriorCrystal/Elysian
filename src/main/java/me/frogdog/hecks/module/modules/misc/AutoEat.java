package me.frogdog.hecks.module.modules.misc;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.property.NumberProperty;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class AutoEat extends ToggleableModule {
    private final NumberProperty<Integer> hunger = new NumberProperty<>(15, 0, 20, "Health");

    public AutoEat() {
        super("AutoEat", new String[] {"AutoEat"}, "Automatically eats when the player gets hungry", ModuleType.MISC);
        this.offerProperties(this.hunger, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.getFoodStats().getFoodLevel() < hunger.getValue()) {

            for (int i = 0; i < 9; i++) {
                ItemStack slotStack = mc.player.inventory.getStackInSlot(i);

                if (slotStack.isEmpty()) {
                    continue;
                }

                if (slotStack.getItem() instanceof ItemFood && !isEating()) {
                    mc.player.inventory.currentItem = i;
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                }
            }
        }
    }

    private boolean isEating() {
        if (mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFood && mc.player.isHandActive()) {
            return true;
        }
        return false;
    }
}
