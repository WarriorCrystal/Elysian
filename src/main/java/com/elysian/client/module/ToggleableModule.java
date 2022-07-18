package com.elysian.client.module;

import com.elysian.client.Elysian;
import com.elysian.client.event.EventProcessor;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.keybind.Keybind;
import com.elysian.client.property.Property;
import com.elysian.client.util.interfaces.Toggleable;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ToggleableModule extends Module implements Toggleable {

    private boolean running;
    private boolean drawn;
    private final int color;
    private final ModuleType moduleType;
    protected final Property<String> keybind = new Property<String>("Keybind", "keybind", "bind", "Keybind");

    private ToggleableModule(String label, String[] aliases, String tooltip, boolean drawn, int color, ModuleType moduleType) {
        super(label, aliases, tooltip);
        this.drawn = drawn;
        this.color = color;
        this.moduleType = moduleType;
        Elysian.getInstance().getKeybindManager().register(new Keybind(label, 0){

            @Override
            public void onPressed() {
                ToggleableModule.this.toggle();
            }
        });
    }

    public ToggleableModule(String label, String[] aliases, String tooltip, ModuleType moduleType) {
        this(label, aliases, tooltip, false, 0, moduleType);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
        if (this.isRunning()) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    @Override
    public void toggle() {
        this.setRunning(!this.running);
    }

    public boolean isDrawn() {
        return this.drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public int getColor() {
        return this.color;
    }

    public ModuleType getModuleType() {
        return this.moduleType;
    }

    protected void onEnable() {}

    protected void onDisable() {}

    public void update(TickEvent event) {}

    public void packet(PacketEvent event) {}

    public void render(RenderWorldLastEvent event) {}

    public void input(com.elysian.client.event.events.InputEvent event) {}
}
