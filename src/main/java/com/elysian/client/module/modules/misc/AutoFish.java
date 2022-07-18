package com.elysian.client.module.modules.misc;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

public final class AutoFish extends ToggleableModule {
    private final Property<Boolean> findRod = new Property<>(true, "FindRod", "fr");
    private final Property<Boolean> autoRepair = new Property<>(true, "AutoRepair", "ar");
    private final NumberProperty<Integer> recastDelay = new NumberProperty<>(5000, 0, 10000, "RecastDelay", "rd");
    private final NumberProperty<Integer> reelDelay = new NumberProperty<>(300, 0, 1000, "ReelDelay", "rd");

    private long recastTimer = 0;
    private long reelTimer = 0;
    private long timeSinceCast = 0;

    public AutoFish() {
        super("AutoFish", new String[] {"AutoFish", "autofish"}, "What do you think this does?", ModuleType.MISC);
        this.offerProperties(this.findRod, this.autoRepair, this.recastDelay, this.reelDelay, this.keybind);
    }

    @Override
    public void update(TickEvent event) {

        if (autoRepair.getValue()) {
            if (!mc.player.getHeldItem(EnumHand.OFF_HAND).isItemDamaged()) {
                for (int i = 0; i < 36; i++) {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);
                    if (stack.getItem().isDamaged(stack) && mc.player.getHeldItemMainhand() != stack) {
                        for (Map.Entry<?, ?> me : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                            if (((Enchantment) me.getKey()).getName().equalsIgnoreCase("enchantment.mending")) {

                                break;
                            }
                        }
                    }
                }
            }
        }

        boolean reelIn = false;

        if (mc.player.fishEntity != null && mc.player.fishEntity.isEntityAlive()) {
            double x = mc.player.fishEntity.motionX;
            double y = mc.player.fishEntity.motionY;
            double z = mc.player.fishEntity.motionZ;
            if (y < -0.075 && (mc.player.fishEntity.isInWater()) && x == 0 && z == 0) {
                reelTimer = System.currentTimeMillis();
                return;
            }
        }

        if (System.currentTimeMillis() - reelDelay.getValue() <= reelTimer && reelTimer != 0) {
            reelIn = true;
            reelTimer = 0;
        }

        if (reelIn) {
            if (timeSinceCast > 0 && System.currentTimeMillis() - timeSinceCast > 3000) {
                mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
                return;
            }
        }

        boolean cast;

        if (mc.player.fishEntity == null) {
            cast = false;
            if (recastTimer == 0) {
                recastTimer = System.currentTimeMillis();
                return;
            }
            if (System.currentTimeMillis() - recastDelay.getValue() >= recastTimer) {
                cast = true;
                recastTimer = 0;
            }
        } else {
            cast = false;
        }

        if (cast) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
                timeSinceCast = System.currentTimeMillis();
            } else {
                if (findRod.getValue()) {
                    if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemFishingRod)) {
                        for (int i = 0; i < 9; i++) {
                            ItemStack stack = mc.player.inventory.getStackInSlot(i);
                            if (stack.getItem() instanceof ItemFishingRod) {
                                mc.player.inventory.currentItem = i;
                                break;
                            }
                        }
                    }
                }
            }
        }

    }
}
