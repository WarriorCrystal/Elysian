package com.elysian.client.module.modules.misc;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public final class FakePlayer extends ToggleableModule {

    public FakePlayer() {
        super("FakePlayer", new String[]{"FakePlayer", "fakeplayer"}, "Clones you", ModuleType.MISC);
        this.offerProperties(this.keybind);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("0f75a81d-70e5-43c5-b892-f33c524284f2"), mc.player.getName() + "'s Clone"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        mc.world.addEntityToWorld(-100, fakePlayer);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.world.removeEntityFromWorld(-100);
    }
}