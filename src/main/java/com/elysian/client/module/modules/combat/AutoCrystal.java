package com.elysian.client.module.modules.combat;

import com.elysian.client.module.ModuleExample;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

public class AutoCrystal extends ToggleableModule {

    private final NumberProperty<Float> breakRange  = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Break Range");
    private final NumberProperty<Float> placeRange  = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Place Range");
    private final NumberProperty<Float> breakRangeWall  = new NumberProperty<Float>(3.00f, 0.00f, 6.00f, "Break Range Wall");
    private final NumberProperty<Float> placeRangeWall  = new NumberProperty<Float>(3.00f, 0.00f, 6.00f, "Place Range Wall");
    private final NumberProperty<Float> targetRange = new NumberProperty<Float>(15.00f, 0.00f, 20.00f, "Target Range");

    private final NumberProperty<Integer> placeDelay  = new NumberProperty<Integer>(0, 0, 10, "Place Delay");
    private final NumberProperty<Integer> breakDelay  = new NumberProperty<Integer>(0, 0, 10, "Break Delay");

    private final Property<Boolean> sortBlocks  = new Property<Boolean>(true, "Sort Blocks", "Sort Blocks");
    private final Property<Boolean> ignoreSelfDamage  = new Property<Boolean>(true, "Ignore self damage", "Example");
    private final NumberProperty<Integer> minPlace  = new NumberProperty<Integer>(5, 0, 50, "Min Place");
    private final NumberProperty<Integer> maxSelfPlace  = new NumberProperty<Integer>(5, 0, 50, "Max Self Place");
    private final NumberProperty<Integer> minBreak = new NumberProperty<Integer>(5, 0, 50, "Min Break");
    private final NumberProperty<Integer> maxSelfBreak  = new NumberProperty<Integer>(5, 0, 50, "Max Self Break");
    private final Property<Boolean> antiSuicide  = new Property<Boolean>(true, "Anti Suicide", "Anti Suicide");

    private final EnumProperty<ModuleExample.Mode> rotateMode  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final NumberProperty<Integer> maxYaw  = new NumberProperty<Integer>(5, 0, 50, "Example");
    private final Property<Boolean> raytrace  = new Property<Boolean>(true, "Example", "Example");
    private final EnumProperty<ModuleExample.Mode> fastMode  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final EnumProperty<ModuleExample.Mode> autoSwitch  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final Property<Boolean> silentSwitchHand  = new Property<Boolean>(true, "Example", "Example");
    private final Property<Boolean> antiWeakness  = new Property<Boolean>(true, "Example", "Example");
    private final NumberProperty<Integer> maxCrystals  = new NumberProperty<Integer>(5, 0, 50, "Example");
    private final Property<Boolean> ignoreTerrain  = new Property<Boolean>(true, "Example", "Example");
    private final EnumProperty<ModuleExample.Mode> crystalLogic  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final Property<Boolean> thirteen  = new Property<Boolean>(true, "Example", "Example");
    private final Property<Boolean> attackPacket  = new Property<Boolean>(true, "Example", "Example");
    private final Property<Boolean> packetSafe  = new Property<Boolean>(true, "Example", "Example");
    private final Property<Boolean> noBreakCalcs  = new Property<Boolean>(true, "Example", "Example");
    private final EnumProperty<ModuleExample.Mode> arrayListMode  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final Property<Boolean> debug  = new Property<Boolean>(true, "Example", "Example");


    public AutoCrystal() {
        super("AutoCrystal", new String[] {"AutoCrystal"}, "AutoCrystal", ModuleType.COMBAT);
        this.offerProperties(keybind);
    }
}
