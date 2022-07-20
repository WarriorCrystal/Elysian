package com.elysian.client.module.modules.misc;

import com.elysian.client.module.ModuleType;
import com.elysian.client.module.ToggleableModule;
import com.elysian.client.util.MessageUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyCoords extends ToggleableModule {

    public CopyCoords() {
        super("CopyCoords", new String[] {"Copy ur coords :V"}, "SEX", ModuleType.MISC);
        this.offerProperties(keybind);
    }

    String coords;

    @Override
    public void onEnable() {
        this.coords = "X: " + mc.player.posX + " Y: " + mc.player.posY + " Z: " + mc.player.posZ;
        String myString = this.coords;
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        MessageUtil.send_client_message("Copied coords to clipboard");
        toggle();

    }

}
