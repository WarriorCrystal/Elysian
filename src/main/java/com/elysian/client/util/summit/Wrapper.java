
package com.elysian.client.util.summit;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    static final Minecraft mc;
    
    public static Minecraft GetMC() {
        return Wrapper.mc;
    }
    
    public static EntityPlayerSP GetPlayer() {
        return Wrapper.mc.player;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
