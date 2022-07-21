package com.elysian.client.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;


import com.elysian.client.property.NumberProperty;
import com.elysian.client.util.spark.RenderUtil;
import com.elysian.client.util.spark.Timer;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.Elysian;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.module.ModuleType;

public class SuperHeroFX extends ToggleableModule {

    private final NumberProperty<Float> delay = new NumberProperty<Float>(1.0f, 0.0f, 10.0f, "Delay");
    private final NumberProperty<Float> scale = new NumberProperty<Float>(1.5f, 0.0f, 10.0f, "Scaling");
    private final NumberProperty<Integer> extra = new NumberProperty<Integer>(5, 0, 50, "Extra");

    public SuperHeroFX() {
        super("SuperHeroFX", new String[] {"SHFX"}, "Best Elysian Module", ModuleType.RENDER);
        offerProperties(delay, scale, extra, keybind);
    }

    private List<PopupText> popTexts = new CopyOnWriteArrayList<>();
    private final Random rand = new Random();
    private final Timer timer = new Timer();
    private static final String[] superHeroTextsBlowup = new String[]{"KABOOM", "BOOM", "POW", "KAPOW", "KABLEM"};
    private static final String[] superHeroTextsDamageTaken = new String[]{"OUCH", "ZAP", "BAM", "WOW", "POW", "SLAP"};

    @Override
    public void update(TickEvent event) {
        this.popTexts.removeIf(PopupText::isMarked);
        this.popTexts.forEach(PopupText::Update);
    }

    @Override
    public void packet(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        try {
            if (event.getPacket() instanceof SPacketExplosion) {
                SPacketExplosion packet = (SPacketExplosion) event.getPacket();
                if (mc.player.getDistance(packet.getX(), packet.getY(), packet.getZ()) < 20.0 && this.timer.passedMs((long) (this.delay.getValue() * 1000.0f))) {
                    this.timer.reset();
                    int len = rand.nextInt(extra.getValue());
                    for (int i = 0; i <= len; i++) {
                        Vec3d pos = new Vec3d(packet.getX() + rand.nextInt(4) - 2, packet.getY() + rand.nextInt(2), packet.getZ() + rand.nextInt(4) - 2);
                        PopupText popupText = new PopupText(ChatFormatting.ITALIC + SuperHeroFX.superHeroTextsBlowup[this.rand.nextInt(SuperHeroFX.superHeroTextsBlowup.length)], pos);
                        popTexts.add(popupText);
                    }
                }
            } else if (event.getPacket() instanceof SPacketEntityStatus) {
                SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
                if (mc.world != null) {
                    Entity e = packet.getEntity((World) mc.world);
                    if (packet.getOpCode() == 35) {
                        if (mc.player.getDistance(e) < 20.0f) {
                            PopupText popupText = new PopupText(ChatFormatting.ITALIC + "POP", e.getPositionVector().add((double) (this.rand.nextInt(2) / 2), 1.0, (double) (this.rand.nextInt(2) / 2)));
                            popTexts.add(popupText);
                        }
                    } else if (packet.getOpCode() == 2) {
                        if (mc.player.getDistance(e) < 20.0f & e != mc.player) {
                            if (this.timer.passedMs((long) (this.delay.getValue() * 1000.0f))) {
                                this.timer.reset();
                                int len = rand.nextInt((int)Math.ceil(extra.getValue()/2.0));
                                for (int i = 0; i <= len; i++) {
                                    Vec3d pos = new Vec3d(e.posX + rand.nextInt(2) - 1, e.posY + rand.nextInt(2) - 1, e.posZ + rand.nextInt(2) - 1);
                                    PopupText popupText = new PopupText(ChatFormatting.ITALIC + SuperHeroFX.superHeroTextsDamageTaken[this.rand.nextInt(SuperHeroFX.superHeroTextsBlowup.length)], pos);
                                    popTexts.add(popupText);
                                }
                            }
                        }
                    }
                }
            } else if (event.getPacket() instanceof SPacketDestroyEntities) {
                SPacketDestroyEntities packet = (SPacketDestroyEntities) event.getPacket();
                final int[] array = packet.getEntityIDs();
                for (int i = 0; i < array.length - 1; i++) {
                    int id = array[i];
                    try {
                    	//wtf is this?
                        if (mc.world.getEntityByID(id) == null) continue;
                    } catch (ConcurrentModificationException exception) {
                        return;
                    }
                    Entity e = mc.world.getEntityByID(id);
                    if (e != null && e.isDead) {
                        if ((mc.player.getDistance(e) < 20.0f & e != mc.player) && e instanceof EntityPlayer) {
                            for (int t = 0; t <= rand.nextInt(extra.getValue()); t++) {
                                Vec3d pos = new Vec3d(e.posX + rand.nextInt(2) - 1, e.posY + rand.nextInt(2) - 1, e.posZ + rand.nextInt(2) - 1);
                                PopupText popupText = new PopupText(ChatFormatting.ITALIC + "" + ChatFormatting.BOLD + "EZ", pos);
                                popTexts.add(popupText);
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException ignoredlel) {
            //rreee empty catch block
        }
    }
    
    @Override
    public void render(RenderWorldLastEvent event) {
        mc.getRenderManager();
        if (mc.getRenderManager().options != null) {

            this.popTexts.forEach(pop -> {
                GlStateManager.pushMatrix();
                RenderUtil.glBillboardDistanceScaled((float) pop.pos.x, (float) pop.pos.y, (float) pop.pos.z, mc.player, (float) scale.getValue());
                GlStateManager.disableDepth();
                GlStateManager.translate(-((double) Elysian.fontManager.getBadaboom().getStringWidth(pop.getDisplayName()) / 2.0), 0.0, 0.0);
                Elysian.fontManager.getBadaboom().drawText(pop.getDisplayName(), 0, 0, pop.color);

                //added this line to not fuck up item rendering
                GlStateManager.enableDepth();

                GlStateManager.popMatrix();
            });
        }
    }
}

