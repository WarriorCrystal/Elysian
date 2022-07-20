package com.elysian.client.module.modules.misc;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.elysian.client.module.ToggleableModule;

import org.lwjgl.input.Keyboard;

import com.elysian.client.module.ModuleType;

public class InventoryMove extends ToggleableModule {

    public InventoryMove() {
        super("InvMove", new String[] {"inventorymove"}, "Move on guis", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.currentScreen instanceof GuiChat || mc.currentScreen == null) {
            return;
        }
        final int[] keys = new int[]{mc.gameSettings.keyBindForward.getKeyCode(), mc.gameSettings.keyBindLeft.getKeyCode(), mc.gameSettings.keyBindRight.getKeyCode(), mc.gameSettings.keyBindBack.getKeyCode(), mc.gameSettings.keyBindJump.getKeyCode()};

        for (int keyCode : keys) {
            if (Keyboard.isKeyDown(keyCode)) {
                KeyBinding.setKeyBindState(keyCode, true);
            } else {
                KeyBinding.setKeyBindState(keyCode, false);
            }
        }

        if (mc.currentScreen instanceof GuiContainer)
        {
            if (Keyboard.isKeyDown(Integer.valueOf(200).intValue())) {
                mc.player.rotationPitch -= 7.0F;
            }
            if (Keyboard.isKeyDown(Integer.valueOf(208).intValue())) {
                mc.player.rotationPitch += 7.0F;
            }
            if (Keyboard.isKeyDown(Integer.valueOf(205).intValue())) {
                mc.player.rotationYaw += 7.0F;
            }
            if (Keyboard.isKeyDown(Integer.valueOf(203).intValue())) {
                mc.player.rotationYaw -= 7.0F;
            }
            if(Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) {
                mc.player.setSprinting(true);
            }
        }
    }

}
