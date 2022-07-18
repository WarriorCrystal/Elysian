package me.frogdog.hecks.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class MixinLoader implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public void injectData(java.util.Map<String, Object> data) {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.hecks.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");

    }

}
