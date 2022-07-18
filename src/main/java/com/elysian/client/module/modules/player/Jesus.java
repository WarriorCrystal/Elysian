package com.elysian.client.module.modules.player;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.Timer;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Jesus extends ToggleableModule {
    private final EnumProperty<Mode> mode = new EnumProperty<> (Mode.Solid, "Mode", "m");
    private final NumberProperty<Float> speed = new NumberProperty<>(0.5f, 0.0f, 1.0f, "Speed");
    private final Property<Boolean> damage = new Property<>(true, "Damage");

    Timer timer = new Timer();

    public Jesus() {
        super("Jesus", new String[] {"Jesus", "jesus"}, "Allows you to walk on water like me", ModuleType.PLAYER);
        this.offerProperties(this.mode, this.speed, this.damage);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.isRiding()) {
            return;
        }

        if (Jesus.this.mode.getValue() == Mode.Bounce) {
            if (mc.player.isInWater()) {
                if (!mc.player.isSneaking()) {
                    mc.player.motionY = 0.03999999910593033D * speed.getValue();
                }
                else {
                    mc.player.motionY = -0.03999999910593033D * speed.getValue();
                }
            }
        }

        if (Jesus.this.mode.getValue() == Mode.Solid) {
            if (!mc.player.isSneaking()) {
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.2f, mc.player.posZ))
                        .getBlock() == Block.getBlockFromName("water") && mc.player.motionY < 0.0D) {
                    mc.player.posY -= mc.player.motionY;
                    mc.player.motionY = 0;
                    if (damage.getValue()) {
                        mc.player.fallDistance = 0;
                    }
                }
                if (mc.player.isInWater()) {
                    mc.player.motionY = 0.03999999910593033D * speed.getValue();
                }
            }
        }

        if (Jesus.this.mode.getValue() == Mode.Dolphin) {
            if (mc.player.isInWater() && !mc.player.isSneaking()) {
                mc.player.motionY += 0.03999999910593033D;
            }
        }
    }

    public enum Mode {
        Solid,
        Dolphin,
        Bounce
    }

}
