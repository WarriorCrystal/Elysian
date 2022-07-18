package me.frogdog.hecks.module.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.util.Timer;

public class DiscordRP extends ToggleableModule {

    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final Timer timer = new Timer();

    public DiscordRP() {
        super("DiscordRP", new String[] {"DiscordRP"}, "Discord Presence", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        start("Hecks.exe");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        stop();
    }

    public static void start(String mode) {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();

        if (mode.equalsIgnoreCase("Hecks.exe")) {
            String discordID = "942195496674533407";
            discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);

            discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
            discordRichPresence.details = "By FrogDog";
            discordRichPresence.largeImageKey = "large";
            discordRichPresence.largeImageText = "Hecks.exe";
            discordRichPresence.state = null;
        }

        discordRPC.Discord_UpdatePresence(discordRichPresence);
    }

    public static void stop() {
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
}
