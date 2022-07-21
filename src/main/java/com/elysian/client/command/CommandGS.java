package com.elysian.client.command;

import net.minecraft.client.Minecraft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public abstract class CommandGS {

    protected static final Minecraft mc = Minecraft.getMinecraft();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Declaration {
        String name();

        String syntax();

        String[] alias();
    }

    private Declaration getDeclaration() {
        return getClass().getAnnotation(Declaration.class);
    }

    private final String name = getDeclaration().name();
    private final String[] alias = getDeclaration().alias();
    private final String syntax = getDeclaration().syntax();

    public String getName() {
        return this.name;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public abstract void onCommand(String command, String[] message);
}