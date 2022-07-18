package com.elysian.client.module.modules.render;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.module.ModuleType;

public class ThunderKill extends ToggleableModule {
    private final Property<Boolean> thunder = new Property<Boolean>(true, "Thunder", "thunder");
    private final NumberProperty<Integer> thunderAmount = new NumberProperty<Integer>(1, 1, 10, "Thunders");
    private final Property<Boolean> sound = new Property<Boolean>(true, "Sound", "sound");
    private final NumberProperty<Integer> soundAmount = new NumberProperty<Integer>(1, 1, 10, "Sounds");


    public ThunderKill() {
        super("ThunderKill", new String[] {"thunderkill"}, "CGrego me lloro por esto", ModuleType.RENDER);
        this.offerProperties(thunder, thunderAmount, sound, soundAmount, this.keybind);
    }
    
    ArrayList<EntityPlayer> playersDead = new ArrayList<>();

    final Object sync = new Object();
    
    @Override
    protected void onEnable() {
        super.onEnable();
        playersDead.clear();
    }

    @Override
    public void update(TickEvent event) {
        if (mc.world == null) {
            playersDead.clear();
            return;
        }

        mc.world.playerEntities.forEach(entity -> {
            if (playersDead.contains(entity)) {
                if (entity.getHealth() > 0)
                    playersDead.remove(entity);
            } else {
                if (entity.getHealth() == 0) {
                    if (thunder.getValue())
                        for(int i = 0; i < thunderAmount.getValue(); i++)
                            mc.world.spawnEntity(new EntityLightningBolt(mc.world, entity.posX, entity.posY, entity.posZ, true));
                    if (sound.getValue())
                        for(int i = 0; i < soundAmount.getValue(); i++)
                            mc.player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, 1.f);
                    playersDead.add(entity);
                }
            }
        });

    }
}


