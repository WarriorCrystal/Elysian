package com.elysian.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.elysian.client.Elysian;
import com.elysian.client.event.events.PacketEvent;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

@Mixin(value = NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onPacketSend(Packet<?> packet, CallbackInfo info) {
        PacketEvent packetSendEvent = new PacketEvent(packet);
        Elysian.getInstance().getModuleManager().packet(packetSendEvent);

        if (packetSendEvent.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketReceive(ChannelHandlerContext chc, Packet<?> packet, CallbackInfo info) {
        PacketEvent packetReceiveEvent = new PacketEvent(packet);
        Elysian.getInstance().getModuleManager().packet(packetReceiveEvent);

        if (packetReceiveEvent.isCancelled()) {
           info.cancel();
        }
    }
}
