package com.elysian.client.module.modules.combat;

import com.elysian.client.util.ModuleManifest;
import com.elysian.client.accessors.ICPacketUseEntity;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.event.events.Render3DEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.util.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;

public class AutoCrystal extends ToggleableModule {


    private final NumberProperty<Integer> enemyRange = new NumberProperty<Integer>(9, 0, 15, "Enemy Range");
    private final NumberProperty<Integer> delay = new NumberProperty<Integer>(0, 0, 100, "Delay");

    private final NumberProperty<Float> placeRange = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Place Range");
    private final NumberProperty<Float> hitRange = new NumberProperty<Float>(5.00f, 0.00f, 6.00f, "Hit Range");
    private final NumberProperty<Float> faceplaceHP = new NumberProperty<Float>(8.00f, 0.00f, 36.00f, "FacePlace Hp");
    private final NumberProperty<Float> minDamage = new NumberProperty<Float>(4.00f, 0.00f, 20.00f, "Min DMG");
    private final NumberProperty<Float> maxSelf = new NumberProperty<Float>(7.00f, 0.00f, 36.00f, "Max Self");

    public AutoCrystal() {
        super("ElPepeCrystal", new String[] {"autocrystal"}, "ete sech", ModuleType.COMBAT);
        this.offerProperties(enemyRange, delay, placeRange, hitRange, faceplaceHP, minDamage, maxSelf, keybind);
    }

    private final ArrayList<BlockPos> placePositions = new ArrayList<>();
    private final ArrayList<Entity> predicted = new ArrayList<>();
    private final ArrayList<Entity> crystals = new ArrayList<>();
    private final TimerUtil breakTimer = new TimerUtil();
    private final TimerUtil timerUtil = new TimerUtil();
    private BlockPos renderPos;



    @SubscribeEvent
    public void update(TickEvent event) {
        if (this.timerUtil.hasReached(1000L)) {
            this.placePositions.clear();
            this.crystals.clear();
            this.renderPos = null;
            breakTimer.reset();
            this.timerUtil.reset();
        }
        if (!ItemUtil.isHoldingItem(Items.END_CRYSTAL)) {
            return;
        }
        final EntityPlayer closestEnemy = CombatUtil.getEnemy((float)enemyRange.getValue());
        if (closestEnemy == null) {
            this.renderPos = null;
            return;
        }
        float maxDamage = (EntityUtil.totalHealth(closestEnemy) > faceplaceHP.getValue()) ? this.minDamage.getValue() : 2.0f;
        BlockPos placePos = null;
        for (final BlockPos pos : BlockUtil.getSphere(placeRange.getValue())) {
            if (!this.canPlaceCrystal(pos)) {
                continue;
            }
            final float targetDamage = this.calculate(pos, (EntityLivingBase)closestEnemy);
            if (targetDamage <= maxDamage) {
                continue;
            }
            final float selfDamage = this.calculate(pos, (EntityLivingBase)mc.player);
            if (EntityUtil.totalHealth((EntityPlayer)mc.player) - 0.5 <= selfDamage || this.maxSelf.getValue() <= selfDamage || targetDamage <= selfDamage) {
                continue;
            }
            maxDamage = targetDamage;
            placePos = pos;
        }
        for (final Entity crystal : mc.world.loadedEntityList) {
            if (crystal instanceof EntityEnderCrystal) {
                if (this.predicted.contains(crystal)) {
                    return;
                }
                if (mc.player.getDistance(crystal) > (mc.player.canEntityBeSeen(crystal) ? this.hitRange.getValue() : 3.0f)) {
                    continue;
                }
                if (this.crystals.contains(crystal) && !this.breakTimer.hasReached((long)this.delay.getValue())) {
                    return;
                }
                mc.getConnection().sendPacket((Packet)new CPacketUseEntity(crystal));
                this.crystals.add(crystal);
                this.breakTimer.reset();
            }
        }
        if (placePos != null) {
            mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(placePos, EnumFacing.UP, (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 1.0f, 0.5f));
            this.placePositions.add(placePos);
            this.renderPos = placePos;
        }
        else {
            this.renderPos = null;
        }
    }

    @SubscribeEvent
    public void onRender3D(final Render3DEvent event) {
        if (this.renderPos != null) {
            RenderUtil.drawWireframeOutlinedBox(this.renderPos, new Color(ColorUtil.getPurpleWave(0)), 1.0f);
        }
    }

    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)mc.world) instanceof EntityEnderCrystal) {
                mc.getConnection().sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity entity : new ArrayList<Entity>(mc.world.loadedEntityList)) {
                    if (entity instanceof EntityEnderCrystal && entity.getDistance(packet.getX(), packet.getY(), packet.getZ()) < 4.0) {
                        entity.setDead();
                    }
                }
            }
        }
        if (event.getPacket() instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packet2 = (SPacketSpawnObject)event.getPacket();
            if (packet2.getType() == 51 && this.placePositions.contains(new BlockPos(packet2.getX(), packet2.getY(), packet2.getZ()).down())) {
                final int id = packet2.getEntityID();
                final ICPacketUseEntity packetUseEntity = (ICPacketUseEntity) new CPacketUseEntity();
                packetUseEntity.setEntityId(id);
                packetUseEntity.setAction(CPacketUseEntity.Action.ATTACK);
                mc.getConnection().sendPacket((Packet)packetUseEntity);
                this.predicted.add(mc.world.getEntityByID(id));
            }
        }
    }

    private float calculate(final BlockPos pos, final EntityLivingBase base) {
        return this.calculate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, base);
    }

    private float calculate(final double x, final double y, final double z, final EntityLivingBase base) {
        double distance = base.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return 0.0f;
        }
        final double density = mc.world.getBlockDensity(new Vec3d(x, y, z), base.getEntityBoundingBox());
        final double densityDistance;
        distance = (densityDistance = (1.0 - distance) * density);
        float damage = this.getDifficultyMultiplier((float)((densityDistance * densityDistance + distance) / 2.0 * 85.0));
        final DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion((World)mc.world, (Entity)mc.player, x, y, z, 6.0f, false, true));
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)base.getTotalArmorValue(), (float)base.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        final int modifierDamage = EnchantmentHelper.getEnchantmentModifierDamage(base.getArmorInventoryList(), damageSource);
        if (modifierDamage > 0) {
            damage = CombatRules.getDamageAfterMagicAbsorb(damage, (float)modifierDamage);
        }
        final PotionEffect resistance = base.getActivePotionEffect(MobEffects.RESISTANCE);
        if (resistance != null) {
            damage = damage * (25 - (resistance.getAmplifier() + 1) * 5) / 25.0f;
        }
        return Math.max(damage, 0.0f);
    }

    private boolean canPlaceCrystal(final BlockPos pos) {
        final BlockPos boost = pos.up();
        if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR || !this.checkEntities(boost)) {
            return false;
        }
        final BlockPos boost2 = boost.up();
        return mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && this.checkEntities(boost2);
    }

    private boolean checkEntities(final BlockPos pos) {
        for (final Object entity : mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            return false;
        }
        return true;
    }

    private float getDifficultyMultiplier(final float distance) {
        switch (mc.world.getDifficulty()) {
            case PEACEFUL: {
                return 0.0f;
            }
            case EASY: {
                return Math.min(distance / 2.0f + 1.0f, distance);
            }
            case HARD: {
                return distance * 3.0f / 2.0f;
            }
            default: {
                return distance;
            }
        }
    }
}
