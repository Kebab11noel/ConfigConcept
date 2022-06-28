package com.noelnemeth.first.config;

import com.noelnemeth.common.ConfigHandler;

public interface Config {
    static <T> Config load(T data, ConfigHandler.ConfigLoader<T> loader) {
        final ConfigHandler<Config> configHandler = new ConfigHandler<>(Config.class);
        configHandler.registerVersion(0, ConfigV0.class);
        return configHandler.loadConfig(data, loader);
    }


    static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    interface Foo {
        int value();
    }

    interface Bar {

    }

    int version();
    Foo foo();
    Bar bar();
}
