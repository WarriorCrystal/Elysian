package com.elysian.client.command;

import com.elysian.client.Elysian;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.util.text.TextComponentString;

public abstract class Command {

    private static final String prefix = "-";
    private final String syntax;
    private final String[] alias;
    private final String mainAlias;

    public Command(String[] alias, String syntax) {
        this.alias = alias;
        this.mainAlias = alias[0];
        this.syntax = syntax;
    }

    public abstract void onClientCommand(String command, String[] args) throws Exception;

    public static void sendClientSideMessage(String message) {
        Elysian.getInstance().mc.player.sendMessage(new TextComponentString(ChatFormatting.WHITE + "" + ChatFormatting.BOLD + "Elysian: "+ ChatFormatting.RESET + message ));
    }

    public static String getPrefix() {
        return prefix;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public String getMainAlias() {
        return this.mainAlias;
    }

    public String getSyntax() {
        return this.syntax;
    }
}
