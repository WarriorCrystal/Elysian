package me.frogdog.hecks.util;

import java.util.ArrayList;
import java.util.List;

public class Registry<T> {

    protected List<T> registry;

    public void register(T item) {
        this.registry.add(item);
    }

    public void unregister(T item) {
        this.registry.remove(item);
    }

    public void clear() {
        this.registry.clear();
    }

    public List<T> getRegistry() {
        return this.registry;
    }
}
