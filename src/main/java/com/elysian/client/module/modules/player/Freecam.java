package com.elysian.client.module.modules.player;

import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class Freecam extends ToggleableModule {
    private final NumberProperty<Integer> speed = new NumberProperty<Integer>(5, 0, 50, "Speed");

    double startX, startY, startZ;
    EntityOtherPlayerMP entity;

    private EntityOtherPlayerMP clonedPlayer;

    private boolean isRidingEntity;
    private Entity ridingEntity;

    public Freecam() {
        super("Freecam", new String[] {"Freecam", "freecam"}, "Spectator mode", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    public void update(TickEvent event) {
        mc.player.noClip = true;
    }

    public void packet(PacketEvent event) {
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof CPacketPlayer.Rotation) {
            event.setCancelled(true);
        }

        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(696984837, entity);
        mc.player.capabilities.allowFlying = true;
        startX = mc.player.posX;
        startY = mc.player.posZ;
        startZ = mc.player.posZ;

    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.noClip = false;
        mc.player.capabilities.allowFlying = false;
        mc.player.capabilities.isFlying = false;
        mc.world.removeEntity(entity);
    }

}
