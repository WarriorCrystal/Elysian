package com.elysian.client.module;

import com.elysian.client.Elysian;
import com.elysian.client.property.NumberProperty;
import com.elysian.client.property.Property;
import com.elysian.client.util.interfaces.Labeled;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.io.File;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Module implements Labeled {

    private final String label;
    private final String tooltip;
    private final String[] aliases;
    private final List<Property<?>> properties = new ArrayList<>();
    protected Minecraft mc = Minecraft.getMinecraft();

    protected Module(String label, String[] aliases, String tooltip) {
        this.label = label;
        this.aliases = aliases;
        this.tooltip = tooltip;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getTooltip() {
        return this.tooltip;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public List<Property<?>> getProperties() {
        return this.properties;
    }

    protected void offerProperties(Property<?> ... properties) {
        this.properties.addAll(Arrays.asList(properties));
    }

    public Property<?> getPropertyByAlias(String alias) {
        for (Property<?> property : properties) {
            for (String propertyAlias : property.getAliases()) {
                if (!alias.equalsIgnoreCase(propertyAlias)) continue;
                return property;
            }
        }
        return null;
    }

    public void loadConfig(JsonObject node) {
        File modsFolder = new File(Elysian.getInstance().getDirectory(), "modules");
        if (!modsFolder.exists()) {
            modsFolder.mkdir();
        }
        node.entrySet().forEach(entry -> {
            Optional<Property> property1 = null;
            for (Property<?> prop : getProperties()) {
                if (property1 != null || !prop.getAliases()[0].equalsIgnoreCase((entry.getKey()).toLowerCase())) continue;
                property1 = Optional.ofNullable(prop);
            }
            if (property1 != null && property1.isPresent()) {
                Object type = (entry.getValue()).getAsString();
                if ((property1.get()).getValue() instanceof Number) {
                    if ((property1.get()).getValue() instanceof Integer) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsInt();
                    } else if (property1.get().getValue() instanceof Long) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsLong();
                    } else if (property1.get().getValue() instanceof Boolean) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsBoolean();
                    } else if (property1.get().getValue() instanceof Double) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsDouble();
                    } else if (property1.get().getValue() instanceof Float) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsFloat();
                    }
                } else {
                    if ((property1.get()).getValue() instanceof Enum) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsString();
                        (property1.get()).setValue(type.toString());
                        return;
                    }
                    if (property1.get().getValue() instanceof Boolean) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsBoolean();
                    } else if (property1.get().getValue() instanceof String) {
                        type = (entry.getValue()).getAsJsonPrimitive().getAsString();
                    }
                }
                property1.get().setValue(type);
            }
        });
    }

    public void saveConfig() {
        File modsFolder = new File(Elysian.getInstance().getDirectory(), "modules");
        if (!modsFolder.exists()) {
            modsFolder.mkdir();
        }
        if (this.getProperties().size() < 1) {
            return;
        }
        File jsonFile = new File(modsFolder, this.getLabel().toLowerCase().replace(" ", "") + ".json");
        if (jsonFile.exists()) {
            jsonFile.delete();
        } else {
            try {
                jsonFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = jsonFile;
        JsonObject node = new JsonObject();
        Collection<Property> settings1 = Collections.unmodifiableCollection(this.getProperties());
        settings1.forEach(setting -> {
            if (setting instanceof NumberProperty) {
                return;
            }
            node.addProperty(setting.getAliases()[0], setting.getValue().toString());
        });
        if (node.entrySet().isEmpty()) {
            return;
        }
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (FileWriter writer = new FileWriter(file);) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)node));
        }
        catch (IOException e) {
            e.printStackTrace();
            file.delete();
        }
    }
}
