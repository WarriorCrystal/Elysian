package com.elysian.client.module.modules.combat;

import com.elysian.client.util.Globals;
import com.elysian.client.util.WurstTimer;
import com.elysian.client.event.EventStage;
import com.elysian.client.event.events.PacketEventWurst;
import com.elysian.client.event.events.UpdateWalkingPlayerEvent;
import com.elysian.client.event.events.CommitEvent;
import com.elysian.client.event.events.EventPriority;
import com.elysian.client.util.Colour;
import com.elysian.client.util.WurstTimer;
import com.elysian.client.event.events.Render3DEvent;

import com.elysian.client.module.ModuleExample;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
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
    private final Property<Boolean> sortBlocks  = new Property<Boolean>(false, "Sort Blocks", "Sort Blocks");
    private final Property<Boolean> ignoreSelfDamage  = new Property<Boolean>(false, "Ignore self damage", "Example");
    private final NumberProperty<Integer> minPlace  = new NumberProperty<Integer>(5, 0, 50, "Min Place");
    private final NumberProperty<Integer> maxSelfPlace  = new NumberProperty<Integer>(5, 0, 50, "Max Self Place");
    private final NumberProperty<Integer> minBreak = new NumberProperty<Integer>(5, 0, 50, "Min Break");
    private final NumberProperty<Integer> maxSelfBreak  = new NumberProperty<Integer>(5, 0, 50, "Max Self Break");
    private final Property<Boolean> antiSuicide  = new Property<Boolean>(false, "Anti Suicide", "Anti Suicide");

    //general
    private final EnumProperty<rotatemode> rotateMode  = new EnumProperty<rotatemode>(rotatemode.Off, "Rotate Mode", "rm");
    private final NumberProperty<Integer> maxYaw  = new NumberProperty<Integer>(180, 0, 180, "Max Yaw");
    private final Property<Boolean> raytrace  = new Property<Boolean>(false, "RayTrace", "RayTrace");
    private final EnumProperty<fastmode> fastMode  = new EnumProperty<fastmode>(fastmode.Off, "Fast Mode", "Fast Mode");
    private final EnumProperty<AutoSwitch> autoSwitch  = new EnumProperty<AutoSwitch>(AutoSwitch.Allways, "Auto Switch", "Auto Switch");
    private final Property<Boolean> silentSwitchHand  = new Property<Boolean>(false, "Silent Switch", "Silent Switch");
    private final Property<Boolean> antiWeakness  = new Property<Boolean>(false, "AntiWeakness", "AntiWeakness");
    private final NumberProperty<Integer> maxCrystals  = new NumberProperty<Integer>(1, 1, 4, "MaxCrystals");
    private final Property<Boolean> ignoreTerrain  = new Property<Boolean>(false, "Ignore Terrain", "Ignore Terrain");
    private final EnumProperty<crystallogic> crystalLogic  = new EnumProperty<crystallogic>(crystallogic.Damage, "Crystal Logic", "Crystal Logic");
    private final Property<Boolean> thirteen  = new Property<Boolean>(false, "1.13", "13");
    private final Property<Boolean> attackPacket  = new Property<Boolean>(false, "Attack Packet", "Attack Packet");
    private final Property<Boolean> packetSafe  = new Property<Boolean>(false, "Packet Safe", "Packet Safe");
    private final Property<Boolean> noBreakCalcs  = new Property<Boolean>(false, "No Break Calcs", "NOBREAKCALCS");
    private final Property<Boolean> debug  = new Property<Boolean>(false, "Debug", "Db");

    //misc
    private final Property<Boolean> threaded  = new Property<Boolean>(false, "Threaded", "Threaded");
    private final Property<Boolean> antiStuck  = new Property<Boolean>(false, "Anti Stuck", "Anti Stuck");
    private final NumberProperty<Integer> maxAntiStuckDamage  = new NumberProperty<Integer>(8, 0, 36, "Stuck Self Damage");

    //predict
    private final Property<Boolean> predictCrystal  = new Property<Boolean>(false, "Predict Crystal", "Predict Crystal");
    private final Property<Boolean> predictBlock  = new Property<Boolean>(false, "Predict Block", "Predict Block");
    private final EnumProperty<PredictTP> predictTeleport  = new EnumProperty<PredictTP>(PredictTP.None, "Predict Teleport", "Predict Teleport");
    private final Property<Boolean> entityPredict  = new Property<Boolean>(false, "Entity Predict", "Entity Predict");
    private final NumberProperty<Integer> predictedTicks = new NumberProperty<Integer>(5, 0, 50, "Predicted Ticks");

    //Feet Obby stuff
    private final Property<Boolean> palceObiFeet   = new Property<Boolean>(false, "Enabled", "Enabled");
    private final Property<Boolean> ObiYCheck   = new Property<Boolean>(false, "YCheck", "YCheck");
    private final Property<Boolean> rotateObiFeet  = new Property<Boolean>(false, "Rotate", "Rotate");
    private final NumberProperty<Integer> timeoutTicksObiFeet  = new NumberProperty<Integer>(3, 0, 5, "Timeout");

    //Faceplace
    private final Property<Boolean> noMP  = new Property<Boolean>(false, "NoMultiPlace", "Example");
    private final NumberProperty<Integer> facePlaceHP  = new NumberProperty<Integer>(0, 0, 36, "FacePlace HP");
    private final NumberProperty<Integer> facePlaceDelay  = new NumberProperty<Integer>(0, 0, 10, "FacePlace Delay");
    private final NumberProperty<Integer> fuckArmourHP  = new NumberProperty<Integer>(0, 0, 100, "Armour%");

    //render
    private final EnumProperty<RenderW> when  = new EnumProperty<RenderW>(RenderW.Break, "Example", "m");
    private final EnumProperty<RenderM> mode  = new EnumProperty<RenderM>(RenderM.Both, "Example", "m");
    private final Property<Boolean> fade  = new Property<Boolean>(false, "Fade", "Fade");
    private final NumberProperty<Integer> fadeTime  = new NumberProperty<Integer>(200, 0, 1000, "Example");
    private final Property<Boolean> flat  = new Property<Boolean>(false, "Flat", "flat");
    private final NumberProperty<Float> height  = new NumberProperty<Float>(0.2f, -2.0f, 2.0f, "Height");
    private final NumberProperty<Integer> width  = new NumberProperty<Integer>(1, 1, 10, "Width");
    private final Property<Boolean> renderDamage  = new Property<Boolean>(false, "Render Damage", "Render Damage");
    private final EnumProperty<Swing> swing  = new EnumProperty<Swing>(Swing.Offhand, "Swing", "Swing");
    private final Property<Boolean> placeSwing = new Property<Boolean>(false, "Place Swing", "Place Swing");


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

    public enum PredictTP {
        Sound,
        Packet,
        None
    }
    public AutoCrystal() {
        super("AutoCrystal", new String[] {"AutoCrystal"}, "AutoCrystal", ModuleType.COMBAT);
        this.offerProperties(breakRange, placeRange, breakRangeWall, placeRangeWall, targetRange, placeDelay, breakDelay, sortBlocks, ignoreSelfDamage, minPlace, maxSelfPlace, minBreak, maxSelfBreak, antiSuicide, maxYaw, raytrace, fastMode, autoSwitch, silentSwitchHand, antiWeakness, maxCrystals, ignoreTerrain, crystalLogic, thirteen, attackPacket, packetSafe, noBreakCalcs, debug, threaded, antiStuck, maxAntiStuckDamage, predictCrystal, predictBlock, predictTeleport, entityPredict, predictedTicks, palceObiFeet, ObiYCheck, rotateObiFeet, timeoutTicksObiFeet, noMP, facePlaceHP, facePlaceDelay, fuckArmourHP, when, mode, fade, fadeTime, flat, height, width, renderDamage, swing, placeSwing, keybind);
    }
}
