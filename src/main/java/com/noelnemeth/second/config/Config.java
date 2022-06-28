package com.noelnemeth.second.config;

import com.noelnemeth.common.ConfigHandler;

public interface Config {
    static <T> Config load(T data, ConfigHandler.ConfigLoader<T> loader) {
        final ConfigHandler<Config> configHandler = new ConfigHandler<>(Config.class);
        configHandler.registerVersion(0, ConfigV0.class);
        configHandler.registerVersion(1, ConfigV1.class);
        configHandler.registerMigrationStep(0, old -> {
            final ConfigV0 configV0 = (ConfigV0) old;
            return new ConfigV1(1, new ConfigV1.Bar(configV0.foo().value()));
        });
        return configHandler.loadConfig(data, loader);
    }

    static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    interface Bar {
        int valueFromOldFoo();
    }

    int version();
    Bar bar();
}
