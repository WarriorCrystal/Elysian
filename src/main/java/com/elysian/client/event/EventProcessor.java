package com.elysian.client.event;

import com.elysian.client.Elysian;
import com.elysian.client.command.Command;
import com.elysian.client.module.Module;
import com.elysian.client.module.ToggleableModule;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class EventProcessor {

    public static final EventProcessor INSTANCE = new EventProcessor();

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (Elysian.getInstance().mc.world != null && Elysian.getInstance().mc.player != null) {
            Elysian.getInstance().getModuleManager().update(event);
        }
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                Elysian.getInstance().mc.ingameGUI.getChatGUI().addToSentMessages(message);
                Elysian.getInstance().getCommandManager().callClientCommand(message.substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendClientSideMessage(ChatFormatting.DARK_GREEN + "Error: " + e.getMessage());
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(!(Keyboard.isRepeatEvent()) && !(Keyboard.isKeyDown(Keyboard.getEventKey()))) {
            com.elysian.client.event.events.InputEvent inputEvent = new com.elysian.client.event.events.InputEvent(com.elysian.client.event.events.InputEvent.Type.KEYBOARD_KEY_PRESS);

            Elysian.getInstance().getKeybindManager().update(inputEvent);
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        int button = Mouse.getEventButton();
        switch (button) {
            case 0:
                com.elysian.client.event.events.InputEvent leftClickEvent = new com.elysian.client.event.events.InputEvent(com.elysian.client.event.events.InputEvent.Type.MOUSE_LEFT_CLICK);
                Elysian.getInstance().getModuleManager().input(leftClickEvent);
                break;
            case 1:
                com.elysian.client.event.events.InputEvent middleClickEvent = new com.elysian.client.event.events.InputEvent(com.elysian.client.event.events.InputEvent.Type.MOUSE_MIDDLE_CLICK);
                Elysian.getInstance().getModuleManager().input(middleClickEvent);
                break;
            case 2:
                com.elysian.client.event.events.InputEvent rightClickEvent = new com.elysian.client.event.events.InputEvent(com.elysian.client.event.events.InputEvent.Type.MOUSE_RIGHT_CLICK);
                Elysian.getInstance().getModuleManager().input(rightClickEvent);
                break;
            default:
                break;
        }
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        if (Elysian.getInstance().mc.world != null) {
            Elysian.getInstance().getHudManager().renderHud(event);
        }
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (Elysian.getInstance().mc.world != null) {
            Elysian.getInstance().getModuleManager().render(event);
        }
    }


}
