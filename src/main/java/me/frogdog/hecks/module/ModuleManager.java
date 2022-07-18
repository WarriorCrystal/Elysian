package me.frogdog.hecks.module;

import com.google.gson.*;
import me.frogdog.hecks.Hecks;
import me.frogdog.hecks.config.Config;
import me.frogdog.hecks.event.events.PacketEvent;
import me.frogdog.hecks.module.modules.client.*;
import me.frogdog.hecks.module.modules.combat.*;
import me.frogdog.hecks.module.modules.exploit.*;
import me.frogdog.hecks.module.modules.misc.*;
import me.frogdog.hecks.module.modules.player.*;
import me.frogdog.hecks.module.modules.render.*;
import me.frogdog.hecks.util.Registry;
import me.frogdog.hecks.util.interfaces.Toggleable;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.ArrayList;

public final class ModuleManager extends Registry<Module> {

    public ModuleManager() {
        this.registry = new ArrayList<Module>();

        register(new Fullbright());
        register(new ClickGui());
        register(new Colours());
        register(new Hud());
        register(new Sprint());
        register(new Strafe());
        register(new NoRender());
        register(new AutoWalk());
        //register(new Scaffold());
        //register(new AntiDesync());
        register(new Notification());
        register(new StorageESP());
        register(new ESP());
        register(new Tracers());
        //register(new KillAura());
        register(new FastPlace());
        register(new Freecam());
        register(new YawLock());
        register(new AutoTotem());
        register(new FakePlayer());
        register(new Jesus());
        register(new VerticalRender());
        register(new SmallOffhand());
        register(new Timer());
        register(new Criticals());
        register(new DiscordRP());
        register(new AutoEat());
        register(new AutoLog());
        //register(new Reach());
        register(new AutoFish());
        register(new KillAura());

        Hecks.getInstance().getKeybindManager().getKeybindByLabel("ClickGui").setKey(Keyboard.KEY_O);

        new Config("module_configurations.json"){

            @Override
            public void load(Object... source) {
                try {
                    if (!this.getFile().exists()) {
                        this.getFile().createNewFile();
                    }
                }
                catch (IOException exception) {
                    exception.printStackTrace();
                }
                File modDirectory = new File(Hecks.getInstance().getDirectory(), "modules");
                if (!modDirectory.exists()) {
                    modDirectory.mkdir();
                }
                Hecks.getInstance().getModuleManager().getRegistry().forEach(mod -> {
                    File file = new File(modDirectory, mod.getLabel().toLowerCase().replaceAll(" ", "") + ".json");
                    if (!file.exists()) {
                        return;
                    }
                    try (FileReader reader = new FileReader(file);){
                        JsonElement node = new JsonParser().parse((Reader)reader);
                        if (!node.isJsonObject()) {
                            return;
                        }
                        mod.loadConfig(node.getAsJsonObject());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                this.loadConfig();
            }

            @Override
            public void save(Object... destination) {
                try {
                    if (!this.getFile().exists()) {
                        this.getFile().createNewFile();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if (!this.getFile().exists()) {
                    return;
                }
                Hecks.getInstance().getModuleManager().getRegistry().forEach(Module::saveConfig);
                this.saveConfig();
            }

            private void loadConfig() {
                JsonElement root;
                File modsFile = new File(this.getFile().getAbsolutePath());
                if (!modsFile.exists()) {
                    return;
                }
                try (FileReader reader = new FileReader(modsFile);){
                    root = new JsonParser().parse((Reader)reader);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                if (!(root instanceof JsonArray)) {
                    return;
                }
                JsonArray mods = (JsonArray)root;
                mods.forEach(node -> {
                    if (!(node instanceof JsonObject)) {
                        return;
                    }
                    try {
                        JsonObject modNode = (JsonObject)node;
                        Hecks.getInstance().getModuleManager().getRegistry().forEach(mod -> {
                            if (mod.getLabel().equalsIgnoreCase(modNode.get("module-label").getAsString()) && mod instanceof Toggleable) {
                                ToggleableModule toggleableModule = (ToggleableModule)mod;
                                if (modNode.get("module-state").getAsBoolean()) {
                                    toggleableModule.setRunning(true);
                                }
                                toggleableModule.setDrawn(modNode.get("module-drawn").getAsBoolean());
                                Hecks.getInstance().getKeybindManager().getKeybindByLabel(toggleableModule.getLabel()).setKey(modNode.get("module-keybind").getAsInt());
                            }
                        });
                    }
                    catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
            }

            private void saveConfig() {
                File modsFile = new File(this.getFile().getAbsolutePath());
                if (modsFile.exists()) {
                    modsFile.delete();
                }
                if (Hecks.getInstance().getModuleManager().getRegistry().isEmpty()) {
                    return;
                }
                JsonArray mods = new JsonArray();
                Hecks.getInstance().getModuleManager().getRegistry().forEach(mod -> {
                    try {
                        JsonObject modObject = new JsonObject();
                        modObject.addProperty("module-label", mod.getLabel());
                        if (mod instanceof Toggleable) {
                            ToggleableModule toggleableModule = (ToggleableModule)mod;
                            modObject.addProperty("module-state", Boolean.valueOf(toggleableModule.isRunning()));
                            modObject.addProperty("module-drawn", Boolean.valueOf(toggleableModule.isDrawn()));
                            modObject.addProperty("module-keybind", (Number)Hecks.getInstance().getKeybindManager().getKeybindByLabel(toggleableModule.getLabel()).getKey());
                        }
                        mods.add((JsonElement)modObject);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                try (FileWriter writer = new FileWriter(modsFile);){
                    writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)mods));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Module getModuleByAlias(String alias) {
        for (Module module : registry) {
            for (String moduleAlias : module.getAliases()) {
                if (!alias.equalsIgnoreCase(moduleAlias)) continue;
                return module;
            }
        }
        return null;
    }

    public void update(TickEvent event) {
        for (Module m : this.registry) {
            if (m instanceof Toggleable) {
                ToggleableModule toggleableModule = (ToggleableModule)m;
                if (toggleableModule.isRunning()) {
                    toggleableModule.update(event);
                }
            }
        }
    }

    public void packet(PacketEvent event) {
        for (Module m : this.registry) {
            if (m instanceof Toggleable) {
                ToggleableModule toggleableModule = (ToggleableModule)m;
                if (toggleableModule.isRunning()) {
                    toggleableModule.packet(event);
                }
            }
        }
    }

    public void render(RenderWorldLastEvent event) {
        for (Module m : this.registry) {
            if (m instanceof Toggleable) {
                ToggleableModule toggleableModule = (ToggleableModule)m;
                if (toggleableModule.isRunning()) {
                    toggleableModule.render(event);
                }
            }
        }
    }

    public void input(me.frogdog.hecks.event.events.InputEvent event) {
        for (Module m : this.registry) {
            if (m instanceof Toggleable) {
                ToggleableModule toggleableModule = (ToggleableModule)m;
                if (toggleableModule.isRunning()) {
                    toggleableModule.input(event);
                }
            }
        }
    }
}
