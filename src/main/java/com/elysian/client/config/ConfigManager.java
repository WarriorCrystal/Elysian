package com.elysian.client.config;

import java.util.ArrayList;

import com.elysian.client.util.Registry;

public final class ConfigManager extends Registry<Config> {

    public ConfigManager() {
        this.registry = new ArrayList<Config>();
    }
}
