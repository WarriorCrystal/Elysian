package com.elysian.client.ui.clickgui.item;

import java.util.ArrayList;

import com.elysian.client.Elysian;
import com.elysian.client.module.Module;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.property.EnumProperty;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.ui.clickgui.item.properties.BooleanButton;
import com.elysian.client.ui.clickgui.item.properties.EnumButton;
import com.elysian.client.ui.clickgui.item.properties.KeybindButton;
import com.elysian.client.ui.clickgui.item.properties.NumberSlider;
import com.elysian.client.util.RenderMethods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ModuleButton extends Button {
    private final Module module;
    private java.util.List<Item> items = new ArrayList<Item>();
    private boolean subOpen;
    private int progress;

    public ModuleButton(Module module) {
        super(module.getLabel(), module.getTooltip());
        this.module = module;
        this.progress = 0;
        if (!module.getProperties().isEmpty()) {
            for (Property<?> property : module.getProperties()) {
                if (property.getValue() instanceof Boolean) {
                    this.items.add(new BooleanButton(property));
                }
                if (property.getValue() instanceof String) {
                    this.items.add(new KeybindButton(property, module));
                }
                if (property instanceof EnumProperty) {
                    this.items.add(new EnumButton((EnumProperty)property));
                }
                if (property instanceof NumberProperty) {
                    this.items.add(new NumberSlider((NumberProperty)property));
                }
                if (!(property.getValue() instanceof NumberProperty)) continue;
            }
        }
    }

    public static float calculateRotation(float var0) {
        if ((var0 %= 360.0F) >= 180.0F) {
            var0 -= 360.0F;
        }

        if (var0 < -180.0F) {
            var0 += 360.0F;
        }

        return var0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!this.items.isEmpty()) {

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Elysian.getInstance().MODID , "textures/module.png"));
            GlStateManager.translate(getX() + getWidth() - 6.7F, getY() + 7.7F - 0.3F, 0.0F);
            GlStateManager.rotate(calculateRotation((float)this.progress), 0.0F, 0.0F, 1.0F);
            RenderMethods.drawModalRect(-5, -5, 0.0F, 0.0F, 10, 10, 10, 10, 10.0F, 10.0F);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();

            if (this.subOpen) {
                float height = 1.0f;
                ++progress;
                for (Item item : items) {
                    item.setLocation(this.x + 1.0f, this.y + (height += 15.0f));
                    item.setHeight(15);
                    item.setWidth(this.width - 9);
                    item.drawScreen(mouseX, mouseY, partialTicks);
                }
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (!this.items.isEmpty()) {
            if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
                this.subOpen = !this.subOpen;
            }
            if (this.subOpen) {
                for (Item item : items) {
                    item.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.subOpen) {
            int height = 14;
            for (Item item : items) {
                height += item.getHeight() + 1;
            }
            return height + 2;
        }
        return 14;
    }

    @Override
    public void toggle() {
        if (this.module instanceof ToggleableModule) {
            ((ToggleableModule)this.module).toggle();
        }
    }

    @Override
    public boolean getState() {
        if (this.module instanceof ToggleableModule) {
            return ((ToggleableModule)this.module).isRunning();
        }
        return true;
    }
    
}

