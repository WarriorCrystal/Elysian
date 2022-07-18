package me.frogdog.hecks.keybind;

import me.frogdog.hecks.util.interfaces.Labeled;

public abstract class Keybind implements Labeled {
    private final String label;
    private int key;

    public Keybind(String label, int key) {
        this.label = label;
        this.key = key;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public abstract void onPressed();
}