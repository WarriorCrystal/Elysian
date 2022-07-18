package com.elysian.client.module;

import net.minecraft.tileentity.TileEntityStructure.Mode;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.elysian.client.event.events.InputEvent;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.module.ModuleType;

public class ModuleExample extends ToggleableModule {

    private final NumberProperty<Integer> exampleInteger = new NumberProperty<Integer>(5, 0, 50, "Example");
    private final NumberProperty<Double> exampleDouble = new NumberProperty<Double>(5.0D, 0.0D, 50.0D, "Example");
    private final NumberProperty<Float> exampleFloat = new NumberProperty<Float>(5.00f, 0.00f, 50.00f, "Example");
    private final Property<Boolean> exampleBoolean = new Property<Boolean>(true, "Example", "Example");
    private final EnumProperty<Mode> exampleMode = new EnumProperty<Mode>(Mode.ONE, "Example", "m");

    public enum Mode {
        ONE,
        TWO
    }

    public ModuleExample() {
        super("ModuleExample", new String[] {"Example"}, "Example", ModuleType.MISC);
        this.offerProperties(exampleInteger, exampleDouble, exampleFloat, exampleBoolean, exampleMode, this.keybind);
    }
    
    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @Override
    public void update(TickEvent event) {

    }

    @Override
    public void packet(PacketEvent event) {
        
    }
    
    @Override
    public void render(RenderWorldLastEvent event) {

    }

    @Override
    public void input(InputEvent event) {

    }

}
