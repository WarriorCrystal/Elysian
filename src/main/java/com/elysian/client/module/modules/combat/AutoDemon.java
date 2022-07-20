package com.elysian.client.module.modules.combat;


import com.elysian.client.event.events.EventCancellable;

import com.elysian.client.util.*;
import com.elysian.client.event.events.EventMotionUpdate;
import com.elysian.client.event.events.EventPacket;
import com.elysian.client.event.events.EventRender;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.BlockUtil;
import com.elysian.client.util.Timer2;
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
        Off,
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
        super("AutoDemon", new String[]{"AutoCrystal"}, "CA", ModuleType.COMBAT);
        this.offerProperties(placeDelay, placeRange, placeRangeWall, minPlace, maxSelfPlace, breakDelay, breakRange, breakRangeWall, minBreak, maxSelfBreak, targetRange, ignoreSelfDamage, antiSuicide, rotateMode, raytrace, fastMode, autoSwitch, silentSwitchHand, antiWeakness, maxCrystals, ignoreTerrain, crystalLogic, thirteen, attackPacket, packetSafe, arrayListMode, threaded, antiStuck, maxAntiStuckDamage, predictCrystal, predictBlock, predictTeleport, entityPredict, predictedTicks, palceObiFeet, ObiYCheck, rotateObiFeet, timeoutTicksObiFeet, noMP, facePlaceHP, facePlaceDelay, fuckArmourHP, when, mode, fade, fadeTime, flat, height, width, rainbow, saturation, brightness, speed, red, green, blue, alpha, red2, green2, blue2, alpha2, renderDamage, swing, placeSwing, this.keybind);
    }

        private final List<EntityEnderCrystal> attemptedCrystals = new ArrayList<>();
        private final ArrayList<RenderPos> renderMap = new ArrayList<>();
        private final ArrayList<BlockPos> currentTargets = new ArrayList<>();
        private final Timer2 crystalsPlacedTimer = new Timer2();

        private EntityEnderCrystal stuckCrystal;
        private static EntityPlayer ca_target = null;

        private boolean alreadyAttacking;
        private boolean placeTimeoutFlag;
        private boolean hasPacketBroke;
        private boolean didAnything;
        private boolean facePlacing;

        private long start;
        private long crystalLatency;

        private int placeTimeout;
        private int breakTimeout;
        private int breakDelayCounter;
        private int placeDelayCounter;
        private int facePlaceDelayCounter;
        private int obiFeetCounter;
        private int crystalsPlaced;

        public EntityPlayer ezTarget;
        public ArrayList<BlockPos> staticPos;
        public EntityEnderCrystal staticEnderCrystal;

        public void render(EventRender event) {
            if (renderMap.isEmpty()) return;
            boolean outline = false;
            boolean solid = false;
            switch (autoSwitch.getValue().toString()) {

            }
            List<RenderPos> toRemove = new ArrayList<>();
            for (RenderPos renderPos : renderMap) {
                int fillAlpha = alpha.getValue();
                int boxAlpha = alpha2.getValue();
                if (currentTargets.contains(renderPos.pos)) {
                    renderPos.fadeTimer = 0;
                } else if (!fade.getValue()) {
                    toRemove.add(renderPos);
                } else {
                    renderPos.fadeTimer++;
                    fillAlpha = (int) (fillAlpha - (fillAlpha * (renderPos.fadeTimer / fadeTime.getValue())));
                    boxAlpha = (int) (boxAlpha - (boxAlpha * (renderPos.fadeTimer / fadeTime.getValue())));
                }
                if (renderPos.fadeTimer > fadeTime.getValue())
                    toRemove.add(renderPos);
            }
            renderMap.removeAll(toRemove);
        }

        public void onEnabled() {
            placeTimeout = this.placeDelay.getValue();
            breakTimeout = this.breakDelay.getValue();
            placeTimeoutFlag = false;
            ezTarget = null;
            facePlacing = false;
            attemptedCrystals.clear();
            hasPacketBroke = false;
            alreadyAttacking = false;
            obiFeetCounter = 0;
            crystalLatency = 0;
            start = 0;
            staticEnderCrystal = null;
            staticPos = null;
            crystalsPlaced = 0;
            crystalsPlacedTimer.reset();
        }

        public float getCPS() {
            return crystalsPlaced / (crystalsPlacedTimer.getPassedTimeMs() / 1000f);
        }


        @EventHandler
        private final Listener<EventPacket.SendPacket> send_listener = new Listener<>(event -> {
            CPacketUseEntity packet;
            if (event.get_era() == EventCancellable.Era.EVENT_PRE
                    && event.get_packet() instanceof CPacketUseEntity
                    && (packet = (CPacketUseEntity) event.get_packet()).getAction() == CPacketUseEntity.Action.ATTACK
                    && packet.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) {
                if (AutoDemon.this.fastMode.getValue().equals(FastMode.Ghost)) {
                    Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).setDead();
                    mc.world.removeEntityFromWorld(packet.entityId);
                }
            }
        });


        @EventHandler
        private final Listener<EventMotionUpdate> on_movement = new Listener<>(event -> {
            if (event.stage == 0 && !AutoDemon.this.rotateMode.getValue().equals(Rotate.Off)) {
                this.doCrystalAura();
            }
        });

        @EventHandler
        private final Listener<EventPacket.ReceivePacket> receive_listener = new Listener<>(event -> {

            // crystal predict check
            SPacketSpawnObject spawnObjectPacket;
            if (event.get_packet() instanceof SPacketSpawnObject
                    && (spawnObjectPacket = (SPacketSpawnObject) event.get_packet()).getType() == 51
                    && this.predictCrystal.getValue()) {
                // for each player on the server
                for (EntityPlayer target : new ArrayList<>(mc.world.playerEntities)) {
                    // if the crystal is valid for the given player
                    if (this.isCrystalGood(new EntityEnderCrystal(mc.world, spawnObjectPacket.getX(), spawnObjectPacket.getY(), spawnObjectPacket.getZ()), target) != 0) {
                        // set up the break packet
                        CPacketUseEntity predict = new CPacketUseEntity();
                        predict.entityId = spawnObjectPacket.getEntityID();
                        predict.action = CPacketUseEntity.Action.ATTACK;
                        mc.player.connection.sendPacket(predict);
                        // swing arm
                        // sets up the packet safe
                        if (packetSafe.getValue()) {
                            hasPacketBroke = true;
                            didAnything = true;
                        }
                        // only do it once
                        break;
                    }
                }
            }

            // sets the 'player pos' of a teleporting player to where they're going to tp to
            if (event.get_packet() instanceof SPacketEntityTeleport) {
                SPacketEntityTeleport tpPacket = (SPacketEntityTeleport) event.get_packet();
                Entity e = mc.world.getEntityByID(tpPacket.getEntityId());
                if (e == mc.player) return;
                if (e instanceof EntityPlayer && AutoDemon.this.predictTeleport.getValue().equals(PredictTP.Packet)) {
                    e.setEntityBoundingBox(e.getEntityBoundingBox().offset(tpPacket.getX(), tpPacket.getY(), tpPacket.getZ()));
                }
            }

            // same as above but works on the sound effect rather than the tp packet
            if (event.get_packet() instanceof SPacketSoundEffect) {
                SPacketSoundEffect soundPacket = (SPacketSoundEffect) event.get_packet();
                if (soundPacket.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT && AutoDemon.this.predictTeleport.getValue().equals(PredictTP.Sound)) {
                    mc.world.loadedEntityList.spliterator().forEachRemaining(player -> {
                        if (player instanceof EntityPlayer && player != mc.player) {
                            if (player.getDistance(soundPacket.getX(), soundPacket.getY(), soundPacket.getZ()) <= targetRange.getValue()) {
                                player.setEntityBoundingBox(player.getEntityBoundingBox().offset(soundPacket.getX(), soundPacket.getY(), soundPacket.getZ()));
                            }
                        }
                    });
                }
                // unsure how this would ever lead to a crash but i dont wanna touch it atm
                try {
                    if (soundPacket.getCategory() == SoundCategory.BLOCKS
                            && soundPacket.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                        for (Entity crystal : new ArrayList<>(mc.world.loadedEntityList)) {
                            if (crystal instanceof EntityEnderCrystal)
                                if (crystal.getDistance(soundPacket.getX(), soundPacket.getY(), soundPacket.getZ()) <= breakRange.getValue()) {
                                    crystalLatency = System.currentTimeMillis() - start;
                                    //AutoDemon.this.mode.getValue().equals(PredictTP.Packet)) {
                                    if (AutoDemon.this.fastMode.getValue().equals(FastMode.Sound)) {
                                        crystal.setDead();
                                    }
                                }
                        }
                    }
                } catch (NullPointerException ignored) {
                }
            }

            // attempt at a place predict, currently doesn't place
            if (event.get_packet() instanceof SPacketExplosion) {
                SPacketExplosion explosionPacket = (SPacketExplosion) event.get_packet();
                BlockPos pos = new BlockPos(Math.floor(explosionPacket.getX()), Math.floor(explosionPacket.getY()), Math.floor(explosionPacket.getZ())).down();
                if (this.predictBlock.getValue()) {
                    for (EntityPlayer player : new ArrayList<>(mc.world.playerEntities)) {
                        if (this.isBlockGood(pos, player) > 0) {
                            BlockUtil.placeCrystalOnBlock3(pos, EnumHand.MAIN_HAND, true);
                        }
                    }
                }
            }
        });

        public void onUpdate() {
                if (rainbow.getValue()) {
                    Color rainbowColor = new Color(RenderUtil.getRainbow(speed.getValue() * 100, 0, (float) saturation.getValue() / 100.0f, (float) brightness.getValue() / 100.0f));
                    red.setValue(rainbowColor.getRed());
                    green.setValue(rainbowColor.getGreen());
                    blue.setValue(rainbowColor.getBlue());

                    red2.setValue(rainbowColor.getRed());
                    green2.setValue(rainbowColor.getGreen());
                    blue2.setValue(rainbowColor.getBlue());
                }

                if (AutoDemon.this.rotateMode.getValue().equals(Rotate.Off)) {
                    this.doCrystalAura();
                }

                if (mc.player.isDead || mc.player.getHealth() <= 0) ca_target = null;
            }

        public void doCrystalAura() {
            didAnything = false;

            if (placeDelayCounter > placeTimeout && (facePlaceDelayCounter >= facePlaceDelay.getValue() || !facePlacing)) {
                start = System.currentTimeMillis();
                this.placeCrystal();
            }
            if (breakDelayCounter > breakTimeout && (!hasPacketBroke || !packetSafe.getValue())) {
                if (antiStuck.getValue() && stuckCrystal != null) {
                    this.breakCrystal(stuckCrystal);
                    stuckCrystal = null;
                } else {
                    this.breakCrystal(null);
                }
            }

            if (!didAnything) {
                hasPacketBroke = false;
            }

            breakDelayCounter++;
            placeDelayCounter++;
            facePlaceDelayCounter++;
            obiFeetCounter++;
        }

        private void clearMap(BlockPos checkBlock) {
            List<RenderPos> toRemove = new ArrayList<>();
            if (checkBlock == null || renderMap.isEmpty()) return;
            for (RenderPos pos : renderMap) {
                if (pos.pos.getX() == checkBlock.getX() && pos.pos.getY() == checkBlock.getY() && pos.pos.getZ() == checkBlock.getZ())
                    toRemove.add(pos);
            }
            renderMap.removeAll(toRemove);
        }

        private void placeCrystal() {
            ArrayList<BlockPos> placePositions;
            placePositions = this.getBestBlocks();
            currentTargets.clear();

            if (placePositions == null) {
                return;
            }

            currentTargets.addAll(placePositions);

            if (placePositions.size() > 0) {
                boolean offhandCheck = false;
                int slot = InventoryUtil.findHotbarBlockz(ItemEndCrystal.class);
                int old = mc.player.inventory.currentItem;
                EnumHand hand = null;
                int stackSize = getCrystalCount(false);
                alreadyAttacking = false;
                if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    if (mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && (AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.Allways) || AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.None))) {
                        if (AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.NoGap)) {
                            if (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE) {
                                return;
                            }
                        }
                        if (this.findCrystalsHotbar() == -1) return;
                        mc.player.inventory.currentItem = this.findCrystalsHotbar();

                        //mc.playerController.syncCurrentPlayItem();

                    }
                } else {
                    offhandCheck = true;
                }
                if (AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.Silent)) {
                    if (slot != -1) {
                        if (mc.player.isHandActive() && silentSwitchHand.getValue()) {
                            hand = mc.player.getActiveHand();
                        }
                        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
                    }
                }
                placeDelayCounter = 0;
                facePlaceDelayCounter = 0;
                didAnything = true;
                for (BlockPos targetBlock : placePositions) {
                    if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal || mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal || AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.Silent)) {
                        if (setYawPitch(targetBlock)) {
                            EntityEnderCrystal cCheck = CrystalUtil.isCrystalStuck(targetBlock.up());
                            if (cCheck != null && antiStuck.getValue()) {
                                stuckCrystal = cCheck;
                            }
                            BlockUtil.placeCrystalOnBlock3(targetBlock, offhandCheck ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, placeSwing.getValue());
                            crystalsPlaced++;
                        }
                    }
                }
                int newSize = getCrystalCount(offhandCheck);
                if (AutoDemon.this.autoSwitch.getValue().equals(AutoSwitch.Silent)) {
                    if (slot != -1) {
                        mc.player.connection.sendPacket(new CPacketHeldItemChange(old));
                        if (silentSwitchHand.getValue() && hand != null) {
                            mc.player.setActiveHand(hand);
                        }
                    }
                }

                if (newSize == stackSize) {
                    didAnything = false;
                }
            }
        }

        private int getCrystalCount(boolean offhand) {
            if (offhand) {
                return mc.player.getHeldItemOffhand().stackSize;
            } else {
                return mc.player.getHeldItemMainhand().stackSize;
            }
        }

        private void breakCrystal(EntityEnderCrystal overwriteCrystal) {
            EntityEnderCrystal crystal;
            if (threaded.getValue()) {
                Threads threads = new Threads();
                threads.start();
                crystal = staticEnderCrystal;
            } else {
                crystal = this.getBestCrystal();
            }
            if (overwriteCrystal != null) {
                if (CrystalUtil.calculateDamage(overwriteCrystal, mc.player, ignoreTerrain.getValue()) < maxAntiStuckDamage.getValue()) {
                    crystal = overwriteCrystal;
                }
            }
            if (crystal == null) return;
            if (antiWeakness.getValue() && mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                boolean shouldWeakness = true;
                if (mc.player.isPotionActive(MobEffects.STRENGTH)) {
                    if (Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.STRENGTH)).getAmplifier() == 2) {
                        shouldWeakness = false;
                    }
                }
                if (shouldWeakness) {
                    if (!alreadyAttacking) {
                        this.alreadyAttacking = true;
                    }
                    int newSlot = -1;
                    for (int i = 0; i < 9; i++) {
                        ItemStack stack = mc.player.inventory.getStackInSlot(i);
                        if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
                            newSlot = i;
                            mc.playerController.updateController();
                            break;
                        }
                    }
                    if (newSlot != -1) {
                        mc.player.inventory.currentItem = newSlot;
                    }
                }
            }
            didAnything = true;
            if (setYawPitch(crystal)) {
                EntityUtil.attackEntity(crystal, this.attackPacket.getValue());
                breakDelayCounter = 0;
            }
        }

        public final EntityEnderCrystal getBestCrystal() {
            double bestDamage = 0;
            EntityEnderCrystal bestCrystal = null;
            for (Entity e : mc.world.loadedEntityList) {
                if (!(e instanceof EntityEnderCrystal)) continue;
                EntityEnderCrystal crystal = (EntityEnderCrystal) e;
                for (EntityPlayer target : new ArrayList<>(mc.world.playerEntities)) {
                    if (target == mc.player) continue;

                    if (mc.player.getDistanceSq(target) > MathUtil.square((float) targetRange.getValue())) continue;

                    if (entityPredict.getValue() && AutoDemon.this.rotateMode.getValue().equals(Rotate.Off) && target != mc.player) {
                        float f = target.width / 2.0F, f1 = target.height;
                        target.setEntityBoundingBox(new AxisAlignedBB(target.posX - (double) f, target.posY, target.posZ - (double) f, target.posX + (double) f, target.posY + (double) f1, target.posZ + (double) f));
                        Entity y = CrystalUtil.getPredictedPosition(target, predictedTicks.getValue());
                        target.setEntityBoundingBox(y.getEntityBoundingBox());
                    }

                    double targetDamage = this.isCrystalGood(crystal, target);
                    if (targetDamage <= 0) continue;
                    if (targetDamage > bestDamage) {
                        bestDamage = targetDamage;
                        this.ezTarget = target;
                        bestCrystal = crystal;
                    }
                }
            }

            if (bestCrystal != null && (AutoDemon.this.when.getValue().equals(RenderW.Both) || AutoDemon.this.when.getValue().equals(RenderW.Break))) {
                BlockPos renderPos = bestCrystal.getPosition().down();
                clearMap(renderPos);
                renderMap.add(new RenderPos(renderPos, bestDamage));
            }
            return bestCrystal;
        }

        public final ArrayList<BlockPos> getBestBlocks() {
            ArrayList<RenderPos> posArrayList = new ArrayList<>();
            if (getBestCrystal() != null && AutoDemon.this.fastMode.getValue().equals(FastMode.Off)) {
                placeTimeoutFlag = true;
                return null;
            }

            if (placeTimeoutFlag) {
                placeTimeoutFlag = false;
                return null;
            }

            for (EntityPlayer target : new ArrayList<>(mc.world.playerEntities)) {
                if (target == mc.player) continue;
                if (mc.player.getDistanceSq(target) > MathUtil.square((float) targetRange.getValue())) continue;
                if (entityPredict.getValue() && target != mc.player) {
                    float f = target.width / 2.0F, f1 = target.height;
                    target.setEntityBoundingBox(new AxisAlignedBB(target.posX - (double) f, target.posY, target.posZ - (double) f, target.posX + (double) f, target.posY + (double) f1, target.posZ + (double) f));
                    Entity y = CrystalUtil.getPredictedPosition(target, predictedTicks.getValue());
                    target.setEntityBoundingBox(y.getEntityBoundingBox());
                }
                ca_target = target;
                for (BlockPos blockPos : CrystalUtil.possiblePlacePositions((float) this.placeRange.getValue(), this.thirteen.getValue(), true)) {
                    double targetDamage = isBlockGood(blockPos, target);
                    if (targetDamage <= 0) continue;
                    posArrayList.add(new RenderPos(blockPos, targetDamage));
                }
            }

            //sorting all place positions
            posArrayList.sort(new DamageComparator());

            //making sure all positions are placeble and wont block each other
            if (maxCrystals.getValue() > 1) {
                List<BlockPos> blockedPosList = new ArrayList<>();
                List<RenderPos> toRemove = new ArrayList<>();
                for (RenderPos test : posArrayList) {
                    boolean blocked = false;
                    for (BlockPos blockPos : blockedPosList) {
                        if (blockPos.getX() == test.pos.getX() && blockPos.getY() == test.pos.getY() && blockPos.getZ() == test.pos.getZ()) {
                            blocked = true;
                        }
                    }
                    if (!blocked) {
                        blockedPosList.addAll(getBlockedPositions(test.pos));
                    } else toRemove.add(test);
                }
                posArrayList.removeAll(toRemove);
            }

            //taking the best out of the list
            int maxCrystals = this.maxCrystals.getValue();
            if (facePlacing && noMP.getValue()) {
                maxCrystals = 1;
            }
            ArrayList<BlockPos> finalArrayList = new ArrayList<>();
            IntStream.range(0, Math.min(maxCrystals, posArrayList.size())).forEachOrdered(n -> {
                RenderPos pos = posArrayList.get(n);
                if (AutoDemon.this.when.getValue().equals(RenderW.Both) || AutoDemon.this.when.getValue().equals(RenderW.Place)) {
                    clearMap(pos.pos);
                    if (pos.pos != null) renderMap.add(pos);
                }
                finalArrayList.add(pos.pos);
            });
            return finalArrayList;
        }

        private ArrayList<BlockPos> getBlockedPositions(BlockPos pos) {
            ArrayList<BlockPos> list = new ArrayList<>();
            list.add(pos.add(1, -1, 1));
            list.add(pos.add(1, -1, -1));
            list.add(pos.add(-1, -1, 1));
            list.add(pos.add(-1, -1, -1));
            list.add(pos.add(-1, -1, 0));
            list.add(pos.add(1, -1, 0));
            list.add(pos.add(0, -1, -1));
            list.add(pos.add(0, -1, 1));
            list.add(pos.add(1, 0, 1));
            list.add(pos.add(1, 0, -1));
            list.add(pos.add(-1, 0, 1));
            list.add(pos.add(-1, 0, -1));
            list.add(pos.add(-1, 0, 0));
            list.add(pos.add(1, 0, 0));
            list.add(pos.add(0, 0, -1));
            list.add(pos.add(0, 0, 1));
            list.add(pos.add(1, 1, 1));
            list.add(pos.add(1, 1, -1));
            list.add(pos.add(-1, 1, 1));
            list.add(pos.add(-1, 1, -1));
            list.add(pos.add(-1, 1, 0));
            list.add(pos.add(1, 1, 0));
            list.add(pos.add(0, 1, -1));
            list.add(pos.add(0, 1, 1));
            return list;
        }

        private double isCrystalGood(EntityEnderCrystal crystal, EntityPlayer target) {

            if (this.isPlayerValid(target)) {
                if (mc.player.canEntityBeSeen(crystal)) {
                    if (mc.player.getDistanceSq(crystal) > MathUtil.square((float) this.breakRange.getValue())) {
                        return 0;
                    }
                } else {
                    if (mc.player.getDistanceSq(crystal) > MathUtil.square((float) this.breakRangeWall.getValue())) {
                        return 0;
                    }
                }
                if (crystal.isDead) return 0;
                if (attemptedCrystals.contains(crystal)) return 0;
                double targetDamage = CrystalUtil.calculateDamage(crystal, target, ignoreTerrain.getValue());
                // set min damage to 2 if we want to kill the dude fast
                facePlacing = false;
                double miniumDamage = this.minBreak.getValue();

                if ((EntityUtil.getHealth(target) <= facePlaceHP.getValue()) || CrystalUtil.getArmourFucker(target, fuckArmourHP.getValue())) {
                    miniumDamage = EntityUtil.isInHole(target) ? 1 : 2;
                    facePlacing = true;
                }

                if (targetDamage < miniumDamage && EntityUtil.getHealth(target) - targetDamage > 0) return 0;
                double selfDamage = 0;
                if (!ignoreSelfDamage.getValue()) {
                    selfDamage = CrystalUtil.calculateDamage(crystal, mc.player, ignoreTerrain.getValue());
                }
                if (selfDamage > maxSelfBreak.getValue()) return 0;
                if (EntityUtil.getHealth(mc.player) - selfDamage <= 0 && this.antiSuicide.getValue()) return 0;
                switch (crystalLogic.getValue().toString()) {
                   /* case "Safe":
                        return targetDamage - selfDamage;
                    case "Damage":
                        return targetDamage;
                    case "Nearby":
                        double distance = mc.player.getDistanceSq(crystal);
                        return targetDamage - distance; */
                }
            }
            return 0;
        }

        private double isBlockGood(BlockPos blockPos, EntityPlayer target) {
            if (this.isPlayerValid(target)) {
                // if raytracing and cannot see block
                if (!CrystalUtil.canSeePos(blockPos) && raytrace.getValue()) return 0;
                // if cannot see pos use wall range, else use normal
                if (!CrystalUtil.canSeePos(blockPos)) {
                    if (mc.player.getDistanceSq(blockPos) > MathUtil.square((float) this.placeRangeWall.getValue())) {
                        return 0;
                    }
                } else {
                    if (mc.player.getDistanceSq(blockPos) > MathUtil.square((float) this.placeRange.getValue())) {
                        return 0;
                    }
                }

                double targetDamage = CrystalUtil.calculateDamage(blockPos, target, ignoreTerrain.getValue());

                facePlacing = false;
                double miniumDamage = this.minPlace.getValue();
                if (EntityUtil.getHealth(target) <= facePlaceHP.getValue() || CrystalUtil.getArmourFucker(target, fuckArmourHP.getValue())) {
                    miniumDamage = EntityUtil.isInHole(target) ? 1 : 2;
                    facePlacing = true;
                }

                if (targetDamage < miniumDamage && EntityUtil.getHealth(target) - targetDamage > 0) return 0;
                double selfDamage = 0;
                if (!ignoreSelfDamage.getValue()) {
                    selfDamage = CrystalUtil.calculateDamage(blockPos, mc.player, ignoreTerrain.getValue());
                }
                if (selfDamage > maxSelfPlace.getValue()) return 0;
                if (EntityUtil.getHealth(mc.player) - selfDamage <= 0 && this.antiSuicide.getValue()) return 0;
                switch (crystalLogic.getValue().toString()) {
                  /*  case:
                        return targetDamage - selfDamage;
                    case "Damage":
                        return targetDamage;
                    case "Nearby":
                        double distance = mc.player.getDistanceSq(blockPos);
                        return targetDamage - distance;*/
                }
            }
            return 0;
        }

        private boolean isPlayerValid(EntityPlayer player) {
            if (player.getHealth() + player.getAbsorptionAmount() <= 0 || player == mc.player) return false;
            if (FriendUtil.isFriend(player.getName())) return false;
            if (player.getName().equals(mc.player.getName())) return false;
            if (player.getDistanceSq(mc.player) > 13 * 13) return false;
            if (this.palceObiFeet.getValue() && obiFeetCounter >= timeoutTicksObiFeet.getValue() && mc.player.getDistance(player) < 5) {
                try {
                    this.blockObiNextToPlayer(player);
                } catch (ConcurrentModificationException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        private void blockObiNextToPlayer(EntityPlayer player) {
            if (ObiYCheck.getValue() && Math.floor(player.posY) == Math.floor(mc.player.posY)) return;
            obiFeetCounter = 0;
            BlockPos pos = EntityUtil.getFlooredPos(player).down();
            if (EntityUtil.isInHole(player) || mc.world.getBlockState(pos).getBlock() == Blocks.AIR) return;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    BlockPos checkPos = pos.add(i, 0, j);
                    if (mc.world.getBlockState(checkPos).getMaterial().isReplaceable()) {
                        BlockUtil.placeBlock(checkPos, PlayerUtil.findObiInHotbar(), rotateObiFeet.getValue(), rotateObiFeet.getValue(), swing);
                    }
                }
            }
        }

        private int findCrystalsHotbar() {
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                    return i;
                }
            }
            return -1;
        }

        private boolean setYawPitch(EntityEnderCrystal crystal) {
            if (AutoDemon.this.rotateMode.getValue().equals(Rotate.Off) || AutoDemon.this.rotateMode.getValue().equals(Rotate.Place)) return true;
            float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), crystal.getPositionEyes(mc.getRenderPartialTicks()));
            float yaw = angle[0];
            float pitch = angle[1];

            RotationUtil.setPlayerRotations(yaw, pitch);

            return true;
        }

        public boolean setYawPitch(BlockPos pos) {
            if (AutoDemon.this.rotateMode.getValue().equals(Rotate.Off)  || AutoDemon.this.rotateMode.getValue().equals(Rotate.Break))  return true;
            float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f));
            float yaw = angle[0];
            float pitch = angle[1];

            RotationUtil.setPlayerRotations(yaw, pitch);

            return true;
        }

        static class RenderPos {
            public RenderPos(BlockPos pos, Double damage) {
                this.pos = pos;
                this.damage = damage;
            }

            Double damage;
            double fadeTimer;
            BlockPos pos;
        }

        static class DamageComparator implements Comparator<RenderPos> {
            @Override
            public int compare(RenderPos a, RenderPos b) {
                return b.damage.compareTo(a.damage);
            }
        }

        public static EntityPlayer get_target() {
            return ca_target;
        }

        final class Threads extends Thread {
            EntityEnderCrystal bestCrystal;

            public Threads() {
            }

            }
        }

