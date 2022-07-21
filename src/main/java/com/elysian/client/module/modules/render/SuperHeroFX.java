package com.elysian.client.module.modules.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.elysian.client.event.events.InputEvent;
import com.elysian.client.event.events.PacketEvent;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.summit.FontManager;
import com.elysian.client.util.summit.MathUtil;
import com.elysian.client.util.summit.PopupText;
import com.elysian.client.util.summit.SalRainbowUtil;
import com.elysian.client.util.summit.Timer;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.module.ModuleType;

public class SuperHeroFX extends ToggleableModule {

    private final NumberProperty<Float> ExplosionDelay = new NumberProperty<Float>(1.0f, 0.0f, 10.0f, "ExplosionDelay");
    private final NumberProperty<Float> HitDelay = new NumberProperty<Float>(1.0f, 1.0f, 10.0f, "HitDelay");
    private final NumberProperty<Float> Scaling = new NumberProperty<Float>(3.0f, 1.0f, 10.0f, "Scaling");

    public SuperHeroFX() {
        super("SuperHeroFX", new String[] {"SHFX"}, "Best Elysian Module", ModuleType.RENDER);
        offerProperties(ExplosionDelay, HitDelay, Scaling, keybind);
    }

    private List<PopupText> popTexts = new CopyOnWriteArrayList<PopupText>();
    private Random rand = new Random();
    private Timer hitTimer = new Timer();
    private Timer explosionTimer = new Timer();
    private static String[] superHeroTextsBlowup;
    private static String[] superHeroTextsDamageTaken;
    private SalRainbowUtil rainbow = new SalRainbowUtil(9);
    Entity entity2;
    Vec3d pos;
    double n;
    double distance;
    double n2;
    double n3;
    Vec3d pos2;
    double posX;
    double posY;
    double posZ;
    double distance2;
    double scale;
    float n4;
    float n5;
    float n6;
    String nameTag;
    float width;
    float height;

    @Override
    public void update(TickEvent event) {
        popTexts.removeIf(pop -> pop.isMarked());
        popTexts.forEach(pop -> pop.Update());
        return;
    }

    SPacketExplosion packet;
    Vec3d pos3;
    Vec3d testpos = new Vec3d(1 + Math.random(), 1 + Math.random() - 2.0, 1 + Math.random());
    final PopupText popupText = new PopupText("fix", testpos);
    SPacketEntityStatus packet2;
    Entity e;
    List<PopupText> popTexts2;
    Vec3d testpos2 = new Vec3d(1 + Math.random(), 1 + Math.random() - 2.0, 1 + Math.random());
    final PopupText popupText2 = new PopupText("fix", testpos2);
    Vec3d pos4;
    List<PopupText> popTexts3;
    Vec3d testpos3 = new Vec3d(1 + Math.random(), 1 + Math.random() - 2.0, 1 + Math.random());
    final PopupText popupText3 = new PopupText("fix", testpos3);
    SPacketDestroyEntities packet3;
    final int[] array = new int[1];
    int length;
    int i = 0;
    int id;
    Entity e2;
    Vec3d pos5;
    List<PopupText> popTexts4;
    Vec3d testpos4 = new Vec3d(1 + Math.random(), 1 + Math.random() - 2.0, 1 + Math.random());
    final PopupText popupText4 = new PopupText("fix", testpos4);

    @Override
    public void packet(PacketEvent event) {
            if (event.getPacket() instanceof SPacketExplosion) {
                packet = (SPacketExplosion)event.getPacket();
                pos3 = new Vec3d(packet.getX() + Math.random(), packet.getY() + Math.random() - 2.0, packet.getZ() + Math.random());
                if (mc.player.getDistance(pos3.x, pos3.y, pos3.z) < 10.0 && explosionTimer.passed(ExplosionDelay.getValue() * 1000.0f)) {
                    explosionTimer.reset();
                    this.popTexts = popTexts;
                    new PopupText(ChatFormatting.ITALIC + superHeroTextsBlowup[rand.nextInt(superHeroTextsBlowup.length)], pos3);
                    popTexts.add(popupText);
                }
            }
            else if (event.getPacket() instanceof SPacketEntityStatus) {
                packet2 = (SPacketEntityStatus)event.getPacket();
                if (mc.world != null) {
                    e = packet2.getEntity(mc.world);
                    if (packet2.getOpCode() == 35) {
                        if (e != null && mc.player.getDistance(e) < 20.0f) {
                            popTexts2 = popTexts;
                            new PopupText(ChatFormatting.ITALIC + "POP", e.getPositionVector().add((double)(rand.nextInt(2) / 2), 1.0, (double)(rand.nextInt(2) / 2)));
                            popTexts2.add(popupText2);
                        }
                    }
                    else if (packet2.getOpCode() == 2 && e != null) {
                        if (mc.player.getDistance(e) < 20.0f & e != mc.player) {
                            pos4 = new Vec3d(e.posX + Math.random(), e.posY + Math.random() - 2.0, e.posZ + Math.random());
                            if (hitTimer.passed(HitDelay.getValue() * 1000.0f)) {
                                hitTimer.reset();
                                popTexts3 = popTexts;
                                new PopupText(ChatFormatting.ITALIC + superHeroTextsDamageTaken[rand.nextInt(superHeroTextsBlowup.length)], pos4);
                                popTexts3.add(popupText3);
                            }
                        }
                    }
                }
            }
            else if (event.getPacket() instanceof SPacketDestroyEntities) {
                packet3 = (SPacketDestroyEntities)event.getPacket();
                packet3.getEntityIDs();
                for (length = array.length; i < length; ++i) {
                    id = array[i];
                    e2 = mc.world.getEntityByID(id);
                    if (e2 != null && e2.isDead) {
                        if ((mc.player.getDistance(e2) < 20.0f & e2 != mc.player) && e2 instanceof EntityPlayer) {
                            pos5 = new Vec3d(e2.posX + Math.random(), e2.posY + Math.random() - 2.0, e2.posZ + Math.random());
                            popTexts4 = popTexts;
                            new PopupText(ChatFormatting.ITALIC + "" + ChatFormatting.BOLD + "EZ", pos5);
                            popTexts4.add(popupText4);
                        }
                    }
                }
            }
    }
    
    @Override
    public void render(RenderWorldLastEvent event) {
        if (mc.getRenderManager() == null || mc.getRenderManager().options == null) {
            return;
        }
        else {
            popTexts.forEach(pop -> {
                entity2 = mc.getRenderViewEntity();
                pos = MathUtil.interpolateVec3dPos(pop.getPos(), event.getPartialTicks());
                n = pos.x;
                distance = pos.y + 0.65;
                n2 = pos.z;
                n3 = distance;
                pos2 = MathUtil.interpolateEntityClose(entity2, event.getPartialTicks());
                posX = entity2.posX;
                posY = entity2.posY;
                posZ = entity2.posZ;
                entity2.posX = pos2.x;
                entity2.posY = pos2.y;
                entity2.posZ = pos2.z;
                distance2 = entity2.getDistance(n, distance, n2);
                scale = 0.04;
                if (distance2 > 0.0) {
                    scale = 0.02 + Scaling.getValue() / 1000.0f * distance2;
                }
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.enablePolygonOffset();
                GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                GlStateManager.disableLighting();
                GlStateManager.translate((float)n, (float)n3 + 1.4f, (float)n2);
                n4 = -mc.getRenderManager().playerViewY;
                n5 = 1.0f;
                n6 = 0.0f;
                GlStateManager.rotate(n4, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                nameTag = pop.getDisplayName();
                width = (float)(FontManager.Get().badaboom.getStringWidth(nameTag) / 2);
                height = (float)FontManager.Get().badaboom.getHeight();
                FontManager.Get().badaboom.drawStringWithShadow(nameTag, -width + 1.0f, -height + 3.0f, pop.getColor());
                GlStateManager.enableDepth();
                GlStateManager.disableBlend();
                GlStateManager.disablePolygonOffset();
                GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                GlStateManager.popMatrix();
                entity2.posX = posX;
                entity2.posY = posY;
                entity2.posZ = posZ;
            });
            return;
        }
    }
    

    static {
        superHeroTextsBlowup = new String[] { "KABOOM", "BOOM", "POW", "KAPOW" };
        superHeroTextsDamageTaken = new String[] { "OUCH", "ZAP", "BAM", "WOW", "POW", "SLAP" };
    }

}
