package com.elysian.client.module.modules.combat;

import com.elysian.client.module.ModuleExample;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

public class AutoCrystal extends ToggleableModule {

    //ranges
    private final NumberProperty<Float> breakRange  = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Break Range");
    private final NumberProperty<Float> placeRange  = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Place Range");
    private final NumberProperty<Float> breakRangeWall  = new NumberProperty<Float>(3.00f, 0.00f, 6.00f, "Break Range Wall");
    private final NumberProperty<Float> placeRangeWall  = new NumberProperty<Float>(3.00f, 0.00f, 6.00f, "Place Range Wall");
    private final NumberProperty<Float> targetRange = new NumberProperty<Float>(15.00f, 0.00f, 20.00f, "Target Range");

    //delay
    private final NumberProperty<Integer> placeDelay  = new NumberProperty<Integer>(0, 0, 10, "Place Delay");
    private final NumberProperty<Integer> breakDelay  = new NumberProperty<Integer>(0, 0, 10, "Break Delay");

    //Damages
    private final Property<Boolean> sortBlocks  = new Property<Boolean>(true, "Sort Blocks", "Sort Blocks");
    private final Property<Boolean> ignoreSelfDamage  = new Property<Boolean>(true, "Ignore self damage", "Example");
    private final NumberProperty<Integer> minPlace  = new NumberProperty<Integer>(5, 0, 50, "Min Place");
    private final NumberProperty<Integer> maxSelfPlace  = new NumberProperty<Integer>(5, 0, 50, "Max Self Place");
    private final NumberProperty<Integer> minBreak = new NumberProperty<Integer>(5, 0, 50, "Min Break");
    private final NumberProperty<Integer> maxSelfBreak  = new NumberProperty<Integer>(5, 0, 50, "Max Self Break");
    private final Property<Boolean> antiSuicide  = new Property<Boolean>(true, "Anti Suicide", "Anti Suicide");

    //general
    private final EnumProperty<rotatemode> rotateMode  = new EnumProperty<rotatemode>(rotatemode.Off, "Rotate Mode", "rm");
    private final NumberProperty<Integer> maxYaw  = new NumberProperty<Integer>(180, 0, 180, "Max Yaw");
    private final Property<Boolean> raytrace  = new Property<Boolean>(true, "RayTrace", "RayTrace");
    private final EnumProperty<fastmode> fastMode  = new EnumProperty<fastmode>(fastmode.Off, "Fast Mode", "Fast Mode");
    private final EnumProperty<AutoSwitch> autoSwitch  = new EnumProperty<AutoSwitch>(AutoSwitch.Allways, "Auto Switch", "Auto Switch");
    private final Property<Boolean> silentSwitchHand  = new Property<Boolean>(true, "Silent Switch", "Silent Switch");
    private final Property<Boolean> antiWeakness  = new Property<Boolean>(true, "AntiWeakness", "AntiWeakness");
    private final NumberProperty<Integer> maxCrystals  = new NumberProperty<Integer>(1, 1, 4, "MaxCrystals");
    private final Property<Boolean> ignoreTerrain  = new Property<Boolean>(true, "Ignore Terrain", "Ignore Terrain");
    private final EnumProperty<crystallogic> crystalLogic  = new EnumProperty<crystallogic>(crystallogic.Damage, "Crystal Logic", "Crystal Logic");
    private final Property<Boolean> thirteen  = new Property<Boolean>(true, "1.13", "13");
    private final Property<Boolean> attackPacket  = new Property<Boolean>(true, "Attack Packet", "Attack Packet");
    private final Property<Boolean> packetSafe  = new Property<Boolean>(true, "Packet Safe", "Packet Safe");
    private final Property<Boolean> noBreakCalcs  = new Property<Boolean>(true, "No Break Calcs", "NOBREAKCALCS");
    private final EnumProperty<ArrayListMode> arrayListMode  = new EnumProperty<ArrayListMode>(ArrayListMode.CPS, "Arraylist Mode", "alm");
    private final Property<Boolean> debug  = new Property<Boolean>(true, "Debug", "Db");

    //misc
    private final Property<Boolean> threaded  = new Property<Boolean>(true, "Threaded", "Threaded");
    private final Property<Boolean> antiStuck  = new Property<Boolean>(true, "Anti Stuck", "Anti Stuck");
    private final NumberProperty<Integer> maxAntiStuckDamage  = new NumberProperty<Integer>(8, 0, 36, "Stuck Self Damage");

    //predict
    private final Property<Boolean> predictCrystal  = new Property<Boolean>(true, "Predict Crystal", "Predict Crystal");
    private final Property<Boolean> predictBlock  = new Property<Boolean>(true, "Predict Block", "Predict Block");
    private final EnumProperty<ModuleExample.Mode> predictTeleport  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Predict Teleport", "Predict Teleport");
    private final Property<Boolean> entityPredict  = new Property<Boolean>(true, "Entity Predict", "Entity Predict");
    private final NumberProperty<Integer> predictedTicks = new NumberProperty<Integer>(5, 0, 50, "Predicted Ticks");

    //Feet Obby stuff
    private final Property<Boolean> palceObiFeet   = new Property<Boolean>(true, "Enabled", "Enabled");
    private final Property<Boolean> ObiYCheck   = new Property<Boolean>(true, "YCheck", "YCheck");
    private final Property<Boolean> rotateObiFeet  = new Property<Boolean>(true, "Rotate", "Rotate");
    private final NumberProperty<Integer> timeoutTicksObiFeet  = new NumberProperty<Integer>(3, 0, 5, "Timeout");

    //Faceplace
    private final Property<Boolean> noMP  = new Property<Boolean>(false, "NoMultiPlace", "Example");
    private final NumberProperty<Integer> facePlaceHP  = new NumberProperty<Integer>(0, 0, 36, "FacePlace HP");
    private final NumberProperty<Integer> facePlaceDelay  = new NumberProperty<Integer>(0, 0, 10, "FacePlace Delay");
    private final NumberProperty<Integer> fuckArmourHP  = new NumberProperty<Integer>(0, 0, 100, "Armour%");

    //render
    private final EnumProperty<ModuleExample.Mode> when  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final EnumProperty<ModuleExample.Mode> mode  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Example", "m");
    private final Property<Boolean> fade  = new Property<Boolean>(true, "Fade", "Fade");
    private final NumberProperty<Integer> fadeTime  = new NumberProperty<Integer>(5, 0, 50, "Example");
    private final Property<Boolean> flat  = new Property<Boolean>(true, "Flat", "flat");
    private final NumberProperty<Float> height  = new NumberProperty<Float>(5.00f, 0.00f, 50.00f, "Height");
    private final NumberProperty<Integer> width  = new NumberProperty<Integer>(5, 0, 50, "Width");
    private final NumberProperty<Integer> renderFillColour  = new NumberProperty<Integer>(5, 0, 50, "Render Fill Colour");
    private final NumberProperty<Integer> renderBoxColour  = new NumberProperty<Integer>(5, 0, 50, "Render Box Colour");
    private final Property<Boolean> renderDamage  = new Property<Boolean>(true, "Render Damage", "Render Damage");
    private final EnumProperty<ModuleExample.Mode> swing  = new EnumProperty<ModuleExample.Mode>(ModuleExample.Mode.ONE, "Swing", "Swing");
    private final Property<Boolean> placeSwing = new Property<Boolean>(true, "Place Swing", "Place Swing");




    public enum rotatemode {
        Off,
        Break,
        Place,
        Both
    }

    public enum fastmode {
        Off,
        Ignore,
        Ghost,
        Sound
    }

    public enum AutoSwitch {
        Allways,
        Nogap,
        None,
        Silent
    }

    public enum crystallogic {
        Damage,
        Nearby,
        Safe
    }

    public enum ArrayListMode {
        Latency,
        Player,
        CPS
    }

    public enum RenderW {
        Place,
        Break,
        Both,
        Never
    }

    public enum RenderM {
        Pretty,
        Break,
        Both,
        Never
    }

    public enum Swing {
        Mainhand,
        Offhand,
        None
    }
    public AutoCrystal() {
        super("AutoCrystal", new String[] {"AutoCrystal"}, "AutoCrystal", ModuleType.COMBAT);
        this.offerProperties(keybind);
    }
}
