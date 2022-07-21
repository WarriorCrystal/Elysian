package com.elysian.client.ui.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.elysian.client.Elysian;
import com.elysian.client.module.Module;
import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.ui.clickgui.item.ModuleButton;
import com.elysian.client.util.RenderMethods;
import com.elysian.client.util.interfaces.Toggleable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public final class ClickGui extends GuiScreen {
    private static ClickGui clickGui;
    private final ArrayList<Panel> panels = new ArrayList();

    public ClickGui() {
        if (this.getPanels().isEmpty()) {
            this.load();
        }
    }

    public static ClickGui getClickGui() {
        return clickGui == null ? (clickGui = new ClickGui()) : clickGui;
    }

    private void load() {
        int x = -82;
        for (final ModuleType moduleType : ModuleType.values()) {
            this.panels.add(new Panel(moduleType.getLabel(), x += 82, 4, true){

                @Override
                public void setupItems() {
                    Elysian.getInstance().getModuleManager().getRegistry().forEach(module -> {
                        ToggleableModule toggleableModule;
                        if (module instanceof Toggleable && (toggleableModule = (ToggleableModule)module).getModuleType().equals((Object)moduleType)) {
                            this.addButton(new ModuleButton(toggleableModule));
                        }
                    });
                }
            });
        }
        this.panels.add(new Panel("Client", x += 82, 4, true){

            @Override
            public void setupItems() {
                Elysian.getInstance().getModuleManager().getRegistry().forEach(module -> {
                    if (!(module instanceof Toggleable)) {
                        this.addButton(new ModuleButton((Module)module));
                    }
                });
            }
        });
        this.panels.forEach(panel -> panel.getItems().sort((item1, item2) -> item1.getLabel().compareTo(item2.getLabel())));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        RenderMethods.drawGradientRect(0.0F, 0.0F, mc.displayWidth, mc.displayHeight, 536870912, -1879048192);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        this.panels.forEach(panel -> panel.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
        this.panels.forEach(panel -> panel.mouseClicked(mouseX, mouseY, clickedButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        this.panels.forEach(panel -> panel.mouseReleased(mouseX, mouseY, releaseButton));
    }

    public void handleMouseInput() throws IOException {
		if (Mouse.getEventDWheel() > 0) {
			for (Panel panels : getPanels()) {
				panels.setY(panels.getY() + 10);
			}
		}
		if (Mouse.getEventDWheel() < 0) {
			for (Panel panels : getPanels()) {
				panels.setY(panels.getY() - 10);
			}
		}
		super.handleMouseInput();
	}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public final ArrayList<Panel> getPanels() {
        return this.panels;
    }
}

