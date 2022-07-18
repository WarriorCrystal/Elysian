package me.frogdog.hecks.module.modules.combat;

import me.frogdog.hecks.module.ModuleType;
import me.frogdog.hecks.module.ToggleableModule;
import me.frogdog.hecks.property.NumberProperty;
import me.frogdog.hecks.property.Property;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class KillAura extends ToggleableModule {
    private final Property<Boolean> monsters = new Property<>(false, "Monsters");
    private final Property<Boolean> players = new Property<>(true, "Players");
    private final Property<Boolean> passives = new Property<>(false, "Passives");
    private final NumberProperty<Float> reach = new NumberProperty<>(5.0f, 3.0f, 6.0f, "Reach");

    public KillAura() {
        super("KillAura", new String[] {"KillAura"}, "Automatically attacks entities", ModuleType.COMBAT);
        this.offerProperties(this.monsters, this.players, this.passives, this.reach, this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        mc.world.getLoadedEntityList().forEach(e -> {
            if (mc.player.getDistanceSq(e) <= reach.getValue() && mc.player != e) {
                if (e instanceof EntityPlayer && players.getValue()) {
                    attack(e);
                }

                if (e instanceof EntityMob && monsters.getValue()) {
                    attack(e);
                }

                if (e instanceof EntityAnimal && passives.getValue()) {
                    attack(e);
                }
            }
        });
    }

    private void attack(Entity entity) {
        if (mc.player.getCooledAttackStrength(0) >= 1) {
            mc.playerController.attackEntity(mc.player, entity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
