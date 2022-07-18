package me.frogdog.hecks;

import me.frogdog.hecks.command.CommandManager;
import me.frogdog.hecks.config.ConfigManager;
import me.frogdog.hecks.event.EventProcessor;
import me.frogdog.hecks.friend.FriendManager;
import me.frogdog.hecks.keybind.KeybindManager;
import me.frogdog.hecks.module.ModuleManager;
import me.frogdog.hecks.ui.hud.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.lwjgl.opengl.Display;

import java.io.File;

@Mod(modid = Hecks.MODID, name = Hecks.NAME, version = Hecks.VERSION)
public final class Hecks {
    public static final String MODID = "hecks";
    public static final String NAME = "Hecks.exe";
    public static final String VERSION = "0.1.0";
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
    public static Hecks instance = null;
    private final CommandManager commandManager;
    private final ConfigManager configManager;
    private final KeybindManager keybindManager;
    private final ModuleManager moduleManager;
    private final FriendManager friendManager;
    private final HudManager hudManager;
    private final File directory;

    public Hecks() {
        EVENT_BUS.register(EventProcessor.INSTANCE);
        instance = this;

        this.directory = new File(System.getProperty("user.home"), "froghecks");

        if (!this.directory.exists()) {
            this.directory.mkdir();
        }

        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        this.keybindManager = new KeybindManager();
        this.moduleManager = new ModuleManager();
        this.friendManager = new FriendManager();
        this.hudManager = new HudManager();
        this.getConfigManager().getRegistry().forEach(config -> config.load(new Object[0]));

        Runtime.getRuntime().addShutdownHook(new Thread("Shutdown Hook Thread"){

            @Override
            public void run() {
                getConfigManager().getRegistry().forEach(config -> config.save(new Object[0]));
            }
        });

        Display.setTitle(NAME + " " + VERSION);
    }

    public static Hecks getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public KeybindManager getKeybindManager() {
        return this.keybindManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public HudManager getHudManager() {
        return this.hudManager;
    }

    public File getDirectory() {
        return this.directory;
    }
}
