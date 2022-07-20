package com.elysian.client.module.modules.combat;


import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.event.events.EventCancellable;
import com.elysian.client.module.ModuleExample;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.IntStream;
public class AutoDemon extends ToggleableModule {


    //ranges adokjisdjkasjdkajknh
    private final NumberProperty<Integer> placeDelay  = new NumberProperty<Integer>(1, 0, 10, "placeDelay");
    private final NumberProperty<Float> placeRange  = new NumberProperty<Float>(5.1f, 0.0f, 6.0f, "placeRange");
    private final NumberProperty<Float> placeRangeWall  = new NumberProperty<Float>(3.5f, 0.0f, 6.0f, "placeRangeWall");
    private final NumberProperty<Integer> minPlace  = new NumberProperty<Integer>(10, 0, 36, "minPlace");
    private final NumberProperty<Integer> maxSelfPlace  = new NumberProperty<Integer>(5, 0, 36, "maxSelfPlace");
    private final NumberProperty<Integer> breakDelay  = new NumberProperty<Integer>(1, 0, 10, "breakDelay");
    private final NumberProperty<Float> breakRange = new NumberProperty<Float>(5.1f, 0.0f, 6.0f, "breakRange");
    private final NumberProperty<Float> breakRangeWall  = new NumberProperty<Float>(3.5f, 0.0f, 6.0f, "breakRangeWall");
    private final NumberProperty<Integer> minBreak  = new NumberProperty<Integer>(6, 0, 36, "minBreak");
    private final NumberProperty<Integer> maxSelfBreak  = new NumberProperty<Integer>(6, 0, 36, "maxSelfBreak");
    private final NumberProperty<Float> targetRange  = new NumberProperty<Float>(12.0f, 0.0f, 20.0f, "targetRange");
    private final Property<Boolean> ignoreSelfDamage  = new Property<Boolean>(false, "ignoreSelfDamage", "ignoreSelfDamage");
    private final Property<Boolean> antiSuicide  = new Property<Boolean>(true, "antiSuicide", "antiSuicide");
    private final EnumProperty<Rotate> rotateMode  = new EnumProperty<Rotate>(Rotate.Off, "rotateMode", "m");
    private final Property<Boolean> raytrace   = new Property<Boolean>(true,"raytrace", "raytrace");
    private final EnumProperty<FastMode> fastMode  = new EnumProperty<FastMode>(FastMode.Ignore, "fastMode", "ray");
    private final EnumProperty<AutoSwitch> autoSwitch   = new EnumProperty<AutoSwitch>(AutoSwitch.Allways, "autoSwitch", "autoSwitch");
    private final Property<Boolean> silentSwitchHand   = new Property<Boolean>(false, "silentSwitchHand", "silentSwitchHand");
    private final Property<Boolean> antiWeakness   = new Property<Boolean>(false, "antiWeakness", "antiWeakness");
    private final NumberProperty<Integer> maxCrystals   = new NumberProperty<Integer>(1, 1, 4, "maxCrystals");
    private final Property<Boolean> ignoreTerrain   = new Property<Boolean>(true, "ignoreTerrain", "ignoreTerrain");
    private final EnumProperty<Logic> crystalLogic    = new EnumProperty<Logic>(Logic.Damage, "crystalLogic", "crystalLogic");
    private final Property<Boolean> thirteen   = new Property<Boolean>(true, "thirteen", "thirteen");
    private final Property<Boolean> attackPacket   = new Property<Boolean>(true, "attackPacket", "attackPacket");
    private final Property<Boolean> packetSafe   = new Property<Boolean>(true, "packetSafe", "packetSafe");
    private final EnumProperty<ArrayListMode> arrayListMode    = new EnumProperty<ArrayListMode>(ArrayListMode.CPS, "arrayListMode", "arrayListMode");

    //misc GETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEADGETOUTOFMYHEAD

    private final Property<Boolean> threaded  = new Property<Boolean>(true, "threaded", "threaded");
    private final Property<Boolean> antiStuck  = new Property<Boolean>(true, "antiStuck", "antiStuck");
    private final NumberProperty<Integer> maxAntiStuckDamage  = new NumberProperty<Integer>(8, 0, 36, "maxAntiStuckDamage");

    //predict %@*)U&&&&&&&&&&&&&&&&&&&

    private final Property<Boolean> predictCrystal  = new Property<Boolean>(true, "predictCrystal", "predictCrystal");
    private final Property<Boolean> predictBlock  = new Property<Boolean>(false, "predictBlock", "predictBlock");
    private final EnumProperty<PredictTP> predictTeleport = new EnumProperty<PredictTP>(PredictTP.None, "predictTeleport", "ptp");
    private final Property<Boolean> entityPredict  = new Property<Boolean>(true, "entityPredict", "entityPredict");
    private final NumberProperty<Integer> predictedTicks  = new NumberProperty<Integer>(2, 0, 5, "predictedTicks");

    //placebosidaisdaisd OBBYPLACE

    private final Property<Boolean> palceObiFeet  = new Property<Boolean>(true, "palceObiFeet", "palceObiFeet");
    private final Property<Boolean> ObiYCheck  = new Property<Boolean>(true, "ObiYCheck", "ObiYCheck");
    private final Property<Boolean> rotateObiFeet  = new Property<Boolean>(true, "rotateObiFeet", "rotateObiFeet");
    private final NumberProperty<Integer> timeoutTicksObiFeet  = new NumberProperty<Integer>(3, 0, 5, "timeoutTicksObiFeet");

    //faceplace EWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWWEWWWWWWWWWWWWWWWWWWWWW

    private final Property<Boolean> noMP   = new Property<Boolean>(true, "noMP", "noMP");
    private final NumberProperty<Integer> facePlaceHP   = new NumberProperty<Integer>(8, 0, 36, "facePlaceHP");
    private final NumberProperty<Integer> facePlaceDelay   = new NumberProperty<Integer>(1, 0, 10, "facePlaceDelay");
    private final NumberProperty<Integer> fuckArmourHP   = new NumberProperty<Integer>(15, 0, 100, "fuckArmourHP");

    //render HATEHATEHATE

    private final EnumProperty<RenderW> when = new EnumProperty<RenderW>(RenderW.Both, "RenderW", "rw");
    private final EnumProperty<RenderM> mode = new EnumProperty<RenderM>(RenderM.Outline, "RenderM", "rm");

    private final Property<Boolean> fade   = new Property<Boolean>(true, "fade", "fade");
    private final NumberProperty<Integer> fadeTime  = new NumberProperty<Integer>(200, 0, 1000, "fadeTime");
    private final Property<Boolean> flat   = new Property<Boolean>(true, "flat", "flat");

    private final NumberProperty<Float> height   = new NumberProperty<Float>(-0.2f, -2.0f, 2.0f, "height");
    private final NumberProperty<Integer> width   = new NumberProperty<Integer>(1, 1, 10, "width");

    private final Property<Boolean> rainbow    = new Property<Boolean>(true, "rainbow", "rainbow");
    private final NumberProperty<Integer> saturation    = new NumberProperty<Integer>(50, 0, 100, "saturation");
    private final NumberProperty<Integer> brightness    = new NumberProperty<Integer>(100, 0, 100, "brightness");
    private final NumberProperty<Integer> speed    = new NumberProperty<Integer>(40, 1, 100, "speed");

    private final NumberProperty<Integer> red    = new NumberProperty<Integer>(100, 0, 255, "red");
    private final NumberProperty<Integer> green    = new NumberProperty<Integer>(100, 0, 255, "green");
    private final NumberProperty<Integer> blue    = new NumberProperty<Integer>(100, 0, 255, "blue");
    private final NumberProperty<Integer> alpha    = new NumberProperty<Integer>(100, 0, 255, "alpha");

    private final NumberProperty<Integer> red2    = new NumberProperty<Integer>(100, 0, 255, "red2");
    private final NumberProperty<Integer> green2    = new NumberProperty<Integer>(100, 0, 255, "green2");
    private final NumberProperty<Integer> blue2    = new NumberProperty<Integer>(100, 0, 255, "blue2");
    private final NumberProperty<Integer> alpha2    = new NumberProperty<Integer>(100, 0, 255, "alpha2");

    private final Property<Boolean> renderDamage    = new Property<Boolean>(true, "renderDamage", "renderDamage");
    private final EnumProperty<Swing> swing  = new EnumProperty<Swing>(Swing.Offhand, "swing", "swing");
    private final Property<Boolean> placeSwing = new Property<Boolean>(true, "placeSwing", "placeSwing");

    public enum Rotate {
        Off,
        Break,
        Place,
        Both
    }
    public enum FastMode {
        Ignore,
        Ghost,
        Sound
    }
    public enum AutoSwitch {
        Allways,
        NoGap,
        None,
        Silent
    }
    public enum Logic {
        Damage,
        Nearby,
        Safe
    }
    public enum ArrayListMode {
        Latency,
        Player,
        CPS
    }
    public enum PredictTP {
        Sound,
        Packet,
        None
    }
    public enum RenderW {
        Place,
        Break,
        Both,
        Never
    }
    public enum RenderM {
        Pretty,
        Solid,
        Outline
    }
    public enum Swing {
        Mainhand,
        Offhand,
        None
    }
    public AutoDemon() {
        super("AutoDemon", new String[] {"AutoCrystal"}, "CA", ModuleType.COMBAT);
        this.offerProperties(placeDelay, placeRange, placeRangeWall, minPlace, maxSelfPlace, breakDelay, breakRange, breakRangeWall, minBreak, maxSelfBreak, targetRange, ignoreSelfDamage, antiSuicide, rotateMode, raytrace, fastMode, autoSwitch, silentSwitchHand, antiWeakness, maxCrystals, ignoreTerrain, crystalLogic, thirteen, attackPacket, packetSafe, arrayListMode, threaded, antiStuck, maxAntiStuckDamage, predictCrystal, predictBlock, predictTeleport, entityPredict, predictedTicks, palceObiFeet, ObiYCheck, rotateObiFeet, timeoutTicksObiFeet, noMP, facePlaceHP, facePlaceDelay, fuckArmourHP, when, mode, fade, fadeTime, flat, height, width, rainbow, saturation, brightness, speed, red, green, blue, alpha, red2, green2, blue2, alpha2, renderDamage, swing, placeSwing, this.keybind);
    }
}
