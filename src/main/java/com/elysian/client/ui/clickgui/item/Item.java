package com.elysian.client.ui.clickgui.item;

import com.elysian.client.ui.clickgui.ClickGui;
import com.elysian.client.ui.clickgui.Panel;
import com.elysian.client.util.interfaces.Labeled;

public class Item implements Labeled {
    private final String label;
    private final String tooltip;
    protected float x;
    protected float y;
    protected int width;
    protected int height;

    public Item(String label) {
        this.label = label;
        this.tooltip = "none";
    }

    public Item(String label, String tooltip) {
        this.label = label;
        this.tooltip = tooltip;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
    }

    @Override
    public final String getLabel() {
        return this.label;
    }

    public final String getTooltip() {
        return this.tooltip;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    protected boolean isHovering(int mouseX, int mouseY) {
        for (Panel panel : ClickGui.getClickGui().getPanels()) {
            if (!panel.drag) continue;
            return false;
        }
        return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
    }
}

