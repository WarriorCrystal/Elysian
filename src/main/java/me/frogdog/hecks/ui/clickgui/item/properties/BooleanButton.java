package me.frogdog.hecks.ui.clickgui.item.properties;

import me.frogdog.hecks.module.modules.client.Colours;
import me.frogdog.hecks.property.Property;
import me.frogdog.hecks.ui.clickgui.item.Button;
import me.frogdog.hecks.util.FontUtil;
import me.frogdog.hecks.util.RenderMethods;

public class BooleanButton extends Button {
    private Property property;

    public BooleanButton(Property property) {
        super(property.getAliases()[0]);
        this.property = property;
        this.width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderMethods.drawRect(this.x, this.y, this.x + (float)this.width + 7.4f, this.y + (float)this.height, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Colours.getClientColorCustomAlpha(77) : Colours.getClientColorCustomAlpha(77)) : (!this.isHovering(mouseX, mouseY) ? 0x11555555 : -2007673515));
        FontUtil.drawString(this.getLabel(), this.x + 2.0f, this.y + 4.0f, this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
        this.property.setValue((Boolean)this.property.getValue() == false);
    }

    @Override
    public boolean getState() {
        return (Boolean)this.property.getValue();
    }
}

