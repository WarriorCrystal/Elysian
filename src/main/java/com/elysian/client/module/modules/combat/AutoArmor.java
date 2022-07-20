package com.elysian.client.module.modules.combat;

import com.elysian.client.module.ModuleType;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.util.XuluTimer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;


public class AutoArmor extends ToggleableModule {

    private XuluTimer timer = new XuluTimer();
    private int[] bestArmorDamage;
    private int[] bestArmorSlots;

    private final Property<Boolean> pif = new Property<Boolean>(true, "PickUpFull", "pif");
    private final Property<Boolean> replace = new Property<Boolean>(true, "ReplaceEmpty", "re");
    private final Property<Boolean> preserve = new Property<Boolean>(true, "PreserveDamaged", "pd");
    private final NumberProperty<Integer> preserveDMG = new NumberProperty<Integer>(5, 0, 100, "Damage %");
    private final NumberProperty<Integer> ms = new NumberProperty<Integer>(500, 0, 1000, "MSDelay");

    private int delay_count;

    public AutoArmor() {
        super("AutoArmor", new String[] {"AutoA"}, "AA", ModuleType.COMBAT);
        this.offerProperties(pif, replace, preserve, preserveDMG, ms, this.keybind);
    }

    @Override
    public void update(TickEvent event) {

        if(mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof InventoryEffectRenderer)) return;

        searchSlots();

        for (int i = 0; i < 4; i ++) {
            if (bestArmorSlots[i] != -1) {
                int bestSlot = bestArmorSlots[i];

                if(bestSlot < 9) bestSlot += 36;

                if (!mc.player.inventory.armorItemInSlot(i).isEmpty()) {
                    //Shouldn't happen often unless if you use mode preserve so we'll just make sure we aren't interfering with other switches
                    if (mc.player.inventory.getFirstEmptyStack() == -1 && pif.getValue()) {
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - i, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, bestSlot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - i, 0, ClickType.PICKUP, mc.player);
                        continue;
                    }
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - i, 0, ClickType.QUICK_MOVE, mc.player);
                    if (!timer.hasReached(ms.getValue())) return;
                }
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, bestSlot, 0, ClickType.QUICK_MOVE, mc.player);
                timer.reset();
            }
        }
    }

    private void searchSlots() {
        bestArmorDamage = new int[4];
        bestArmorSlots = new int[4];
        Arrays.fill(bestArmorDamage, -1);
        Arrays.fill(bestArmorSlots, -1);

        for (int i = 0; i < bestArmorSlots.length; i++) {
            ItemStack itemStack = mc.player.inventory.armorItemInSlot(i);

            if (itemStack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) itemStack.getItem();

                if (preserve.getValue()) {
                    float dmg = ((float) itemStack.getMaxDamage() - (float) itemStack.getItemDamage()) / (float) itemStack.getMaxDamage();
                    int percent = (int) (dmg * 100);
                    if (percent > preserveDMG.getValue()) { //keep it at -1 if it's below
                        bestArmorDamage[i] = armor.damageReduceAmount;
                    }
                } else {
                    bestArmorDamage[i] = armor.damageReduceAmount;
                }
            } else if (itemStack.isEmpty()) {
                if (!replace.getValue())
                    bestArmorDamage[i] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < 9 * 4; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

            if (itemStack.getCount() > 1) continue;

            if (itemStack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) itemStack.getItem();
                int armorType = armor.armorType.ordinal() - 2;
                if (bestArmorDamage[armorType] < armor.damageReduceAmount) {
                    bestArmorDamage[armorType] = armor.damageReduceAmount;
                    bestArmorSlots[armorType] = i;
                }
            }
        }
    }
}
