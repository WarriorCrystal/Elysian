package com.elysian.client.util;

import com.elysian.client.Elysian;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordRPC;

import net.minecraft.client.Minecraft;

public class RPC {
    private static final String ClientId = "998646999333220442";
    private static final Minecraft mc;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static String details;
    private static String state;
    
    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = mc.player.getName();
        presence.state = "Menu";
        presence.largeImageKey = "logo";
        presence.largeImageText = Elysian.ELYSIAN;
        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                	rpc.Discord_RunCallbacks();
                	details = mc.player.getName();
                	state = "Menu";
                    if (mc.isIntegratedServerRunning()) {
                    	state = "Singleplayer";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        if (!mc.getCurrentServerData().serverIP.equals("")) {
                        	state = mc.getCurrentServerData().serverIP;
                        }
                    }
                    else {
                    	state = "Menu";
                    }
                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                    }
                    presence.details = details;
                    presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
    
    static {
        mc = Minecraft.getMinecraft();
        rpc = DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
    }
    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}
