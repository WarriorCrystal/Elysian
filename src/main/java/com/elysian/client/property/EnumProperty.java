package com.elysian.client.property;

public class EnumProperty<T extends Enum> extends Property<T> {
    public EnumProperty(T value, String... aliases) {
        super(value, aliases);
    }

    public String getFixedValue() {
        return Character.toString(((Enum)this.value).name().charAt(0)) + ((Enum)this.value).name().toLowerCase().replaceFirst(Character.toString(((Enum)this.value).name().charAt(0)).toLowerCase(), "");
    }

    @Override
    public void setValue(String value) {
        Enum[] array = (Enum[])((Enum)this.getValue()).getClass().getEnumConstants();
        int length = array.length;
        for (int i = 0; i < length; ++i) {
            if (!array[i].name().equalsIgnoreCase(value)) continue;
            this.value = (T)array[i];
        }
    }

    public void increment() {
        Enum[] array = (Enum[])((Enum)this.getValue()).getClass().getEnumConstants();
        int length = array.length;
        for (int i = 0; i < length; ++i) {
            if (!array[i].name().equalsIgnoreCase(this.getFixedValue())) continue;
            if (++i > array.length - 1) {
                i = 0;
            }
            this.setValue(array[i].toString());
        }
    }

    public void decrement() {
        Enum[] array = (Enum[])((Enum)this.getValue()).getClass().getEnumConstants();
        int length = array.length;
        for (int i = 0; i < length; ++i) {
            if (!array[i].name().equalsIgnoreCase(this.getFixedValue())) continue;
            if (--i < 0) {
                i = array.length - 1;
            }
            this.setValue(array[i].toString());
        }
    }
}
