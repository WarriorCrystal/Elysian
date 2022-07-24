package com.elysian.client.module.modules.combat;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.salhack.PlayerUtil;

public class Offhand extends ToggleableModule {


    private final NumberProperty<Float> health = new NumberProperty<Float>(16.0f, 0.0f, 20.0f, "Health");
    private final EnumProperty<TMode> Mode = new EnumProperty<TMode>(TMode.Crystal, "Mode");
    private final EnumProperty<TMode> FallbackMode = new EnumProperty<TMode>(TMode.Crystal, "Fallback");
    private final NumberProperty<Float> FallDistance = new NumberProperty<Float>(15.0f, 0.0f, 100.0f, "FallDistance");
    private final Property<Boolean> TotemOnElytra = new Property<Boolean>(true, "ElytraTotem");
    private final Property<Boolean> OffhandGapOnSword = new Property<Boolean>(true, "SwordGap");
    private final Property<Boolean> OffhandStrNoStrSword = new Property<Boolean>(false, "Strength");
    private final Property<Boolean> HotbarFirst = new Property<Boolean>(false, "HotbarFirst");
    public Offhand() {
        super("OffHand", new String[] {"AutoTotem"}, "100% Not Salhack", ModuleType.COMBAT);
        this.offerProperties(health, Mode, FallbackMode, FallDistance, TotemOnElytra, OffhandGapOnSword, OffhandStrNoStrSword, HotbarFirst, this.keybind);
    }

    public enum TMode
    {
        Totem,
        Gap,
        Crystal,
        Pearl,
        Chorus,
        Strength,
    }
    
    @Override
    public void update(TickEvent event) {
        if (mc.currentScreen != null && (!(mc.currentScreen instanceof GuiInventory)))
            return;
        
        if (!mc.player.getHeldItemMainhand().isEmpty())
        {
            if (health.getValue() <= PlayerUtil.GetHealthWithAbsorption() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && OffhandStrNoStrSword.getValue() && !mc.player.isPotionActive(MobEffects.STRENGTH))
            {
                SwitchOffHandIfNeed(TMode.Strength);
                return;
            }
            
            /// Sword override
            if (health.getValue() <= PlayerUtil.GetHealthWithAbsorption() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && OffhandGapOnSword.getValue())
            {
                SwitchOffHandIfNeed(TMode.Gap);
                return;
            }
        }
        
        /// First check health, most important as we don't want to die for no reason.
        if (health.getValue() > PlayerUtil.GetHealthWithAbsorption() || Mode.getValue() == TMode.Totem || (TotemOnElytra.getValue() && mc.player.isElytraFlying()) || (mc.player.fallDistance >= FallDistance.getValue() && !mc.player.isElytraFlying()))
        {
            SwitchOffHandIfNeed(TMode.Totem);
            return;
        }
        
        /// If we meet the required health
        SwitchOffHandIfNeed(Mode.getValue());
    }

    private void SwitchOffHandIfNeed(TMode p_Val)
    {
        Item l_Item = GetItemFromModeVal(p_Val);
        
        if (mc.player.getHeldItemOffhand().getItem() != l_Item)
        {
            int l_Slot = HotbarFirst.getValue() ? PlayerUtil.GetRecursiveItemSlot(l_Item) : PlayerUtil.GetItemSlot(l_Item);
            
            Item l_Fallback = GetItemFromModeVal(FallbackMode.getValue());
            
            String l_Display = GetItemNameFromModeVal(p_Val);
            
            if (l_Slot == -1 && l_Item != l_Fallback && mc.player.getHeldItemOffhand().getItem() != l_Fallback)
            {
                l_Slot = PlayerUtil.GetRecursiveItemSlot(l_Fallback);
                l_Display = GetItemNameFromModeVal(FallbackMode.getValue());
                
                /// still -1...
                if (l_Slot == -1 && l_Fallback != Items.TOTEM_OF_UNDYING)
                {
                    l_Fallback = Items.TOTEM_OF_UNDYING;
                    
                    if (l_Item != l_Fallback && mc.player.getHeldItemOffhand().getItem() != l_Fallback)
                    {
                        l_Slot = PlayerUtil.GetRecursiveItemSlot(l_Fallback);
                        l_Display = "Emergency Totem";
                    }
                }
            }

            if (l_Slot != -1)
            {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP,
                        mc.player);
                
                /// @todo: this might cause desyncs, we need a callback for windowclicks for transaction complete packet.
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0,
                        ClickType.PICKUP, mc.player);
                mc.playerController.updateController();
                
            }
        }
    }

    public Item GetItemFromModeVal(TMode p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return Items.END_CRYSTAL;
            case Gap:
                return Items.GOLDEN_APPLE;
            case Pearl:
                return Items.ENDER_PEARL;
            case Chorus:
                return Items.CHORUS_FRUIT;
            case Strength:
                return Items.POTIONITEM;
            default:
                break;
        }
        
        return Items.TOTEM_OF_UNDYING;
    }

    private String GetItemNameFromModeVal(TMode p_Val)
    {
        switch (p_Val)
        {
            case Crystal:
                return "End Crystal";
            case Gap:
                return "Gap";
            case Pearl:
                return "Pearl";
            case Chorus:
                return "Chorus";
            case Strength:
                return "Strength";
            default:
                break;
        }
        
        return "Totem";
    }

}
