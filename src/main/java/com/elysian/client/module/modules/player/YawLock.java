package com.elysian.client.module.modules.player;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class YawLock extends ToggleableModule {

    float yaw;

    public YawLock() {
        super("YawLock", new String[] {"YawLock", "lock"}, "Locks the players yaw", ModuleType.PLAYER);
        this.offerProperties(this.keybind);
    }

    @Override
    public void update(TickEvent event) {
        if (mc.player.rotationYaw < yaw - 1.1f ) {
            mc.player.rotationYaw++;
        } else if (mc.player.rotationYaw > yaw + 1.1f ) {
            mc.player.rotationYaw--;
        } else {
            return;
        }
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        EnumFacing direction = mc.player.getHorizontalFacing();
        switch (direction) {
            case EAST:
                mc.player.rotationYaw = 270;
                yaw = 270;
                break;
            case WEST:
                mc.player.rotationYaw = 90;
                yaw = 90;
                break;
            case NORTH:
                mc.player.rotationYaw = 180;
                yaw = 180;
                break;
            case SOUTH:
                mc.player.rotationYaw = 360;
                yaw = 360;
                break;
            default:
                break;
        }

    }
}
