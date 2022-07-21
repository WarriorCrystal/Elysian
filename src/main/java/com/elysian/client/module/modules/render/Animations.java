package com.elysian.client.module.modules.render;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import net.minecraft.entity.player.EntityPlayer;


public class Animations extends ToggleableModule {

    private final Property<Boolean> playersDisableAnimations = new Property<Boolean>(true, "Disable Animations", "");
    private final Property<Boolean> changeMainhand = new Property<Boolean>(true, "Change Mainhand", "");
    private final Property<Boolean> changeOffhand  = new Property<Boolean>(true, "Change Offhand", "");
    private final Property<Boolean> changeSwing  = new Property<Boolean>(true, "Change Swing", "");
    private final NumberProperty<Float> mainhand  = new NumberProperty<Float>(1.00f, 0.00f, 1.00f, "MainHand");
    private final NumberProperty<Float> offhand  = new NumberProperty<Float>(1.00f, 0.00f, 1.00f, "OffHand");
    private final NumberProperty<Integer> swingDelay  = new NumberProperty<Integer>(6, 1, 20, "Swing Delay");

    public Animations() {
        super("Animations", new String[] {"Animations"}, "Animations", ModuleType.MISC);
        this.offerProperties(playersDisableAnimations, changeMainhand, changeOffhand, changeSwing, mainhand, offhand, swingDelay, this.keybind);
    }

    public void onUpdate(){
        if (playersDisableAnimations.getValue()) {
            for (EntityPlayer player : mc.world.playerEntities) {
                player.limbSwing = 0;
                player.limbSwingAmount = 0;
                player.prevLimbSwingAmount = 0;
            }
        }

        if (changeMainhand.getValue() && mc.entityRenderer.itemRenderer.equippedProgressMainHand != mainhand.getValue().floatValue()) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainhand.getValue().floatValue();
            mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
        }

        if (changeOffhand.getValue() && mc.entityRenderer.itemRenderer.equippedProgressOffHand != offhand.getValue().floatValue()) {
            mc.entityRenderer.itemRenderer.equippedProgressOffHand = offhand.getValue().floatValue();
            mc.entityRenderer.itemRenderer.itemStackOffHand = mc.player.getHeldItemOffhand();
        }
    }

}
