package com.elysian.client.ui.clickgui.item;

import com.elysian.client.module.modules.client.Colours;
import com.elysian.client.ui.clickgui.ClickGui;
import com.elysian.client.ui.clickgui.Panel;
import com.elysian.client.util.FontUtil;
import com.elysian.client.util.RenderMethods;
import com.elysian.client.util.interfaces.Labeled;

public class Button extends Item implements Labeled {
    private boolean state;

    public Button(String label) {
        super(label, "none");
        this.height = 15;
    }

    public Button(String label, String tooltip) {
        super(label, tooltip);
        this.height = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderMethods.drawGradientRect(this.x, this.y, this.x + (float)this.width, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colours.getClientColorCustomAlpha(77) : Colours.getClientColorCustomAlpha(55)) : (!this.isHovering(mouseX, mouseY) ? 0x33555555 : 0x77AAAAAB), this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colours.getClientColorCustomAlpha(77) : Colours.getClientColorCustomAlpha(55)) : (!this.isHovering(mouseX, mouseY) ? 0x55555555 : 0x66AAAAAB));
        FontUtil.drawString(this.getLabel(), this.x + 2.0f, this.y + 4.0f, this.getState() ? -1 : -5592406);
        FontUtil.drawString(isHovering(mouseX, mouseY) ? this.getLabel() + ": " + this.getTooltip() : "", 1.0f, 300.0f, 0xFFFFFF);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.state = !this.state;
            this.toggle();
        }
    }

    public void toggle() {
    }

    public boolean getState() {
        return this.state;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    protected boolean isHovering(int mouseX, int mouseY) {
        for (Panel panel : ClickGui.getClickGui().getPanels()) {
            if (!panel.drag) continue;
            return false;
        }
        return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
    }
}

