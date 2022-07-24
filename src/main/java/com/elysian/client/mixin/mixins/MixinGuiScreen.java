package com.elysian.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.elysian.client.Elysian;
import com.elysian.client.util.summit.EventRenderTooltip;
import com.elysian.client.util.summit.Listener;



@Mixin(GuiScreen.class)
public class MixinGuiScreen
{
    @Shadow
    protected Listener<GuiButton> buttonList;

    @Shadow
    public int width;

    @Shadow
    public int height;
    
    @Shadow
    public Minecraft mc;
    
    @Shadow
    protected FontRenderer fontRenderer;

    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo p_Info)
    {
        EventRenderTooltip l_Event = new EventRenderTooltip(stack, x, y);
        Elysian.SAL_EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled())
            p_Info.cancel();
    }
}