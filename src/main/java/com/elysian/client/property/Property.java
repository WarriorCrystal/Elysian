package com.elysian.client.property;

public class Property<T> {
    private final String[] aliases;
    protected T value;

    public Property(T value, String... aliases) {
        this.value   = value;
        this.aliases = aliases;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setValue(String value) {}
}
