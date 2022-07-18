package com.elysian.client.module.modules.combat;

import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public final class Criticals extends ToggleableModule {
    private final EnumProperty<Mode> mode = new EnumProperty(Mode.PACKET, "Mode");

    public Criticals() {
        super("Criticals", new String[] {"criticals", "crits"}, "Crits all entities that you hit", ModuleType.COMBAT);
        this.offerProperties(this.mode);
        this.offerProperties(this.keybind);
    }

    @Override
    public void packet(PacketEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {

                    if (Criticals.this.mode.getValue() == Mode.PACKET) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
                                mc.player.posY + 0.062602401692772D, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
                                mc.player.posY + 0.0726023996066094D, mc.player.posZ, false));
                        mc.player.connection.sendPacket(
                                new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

                    }

                    if (Criticals.this.mode.getValue() == Mode.JUMP) {
                        mc.player.jump();
                        mc.player.connection.sendPacket(
                                new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.05, mc.player.posZ, false));
                        mc.player.connection.sendPacket(
                                new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    }
                }
            }
        }
    }

    public enum Mode {
        PACKET,
        JUMP,

    }
}
