package com.noelnemeth.second.config;

import com.noelnemeth.common.ConfigHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

public interface Config {
    static <T> Config load(T data, ConfigHandler.ConfigLoader<T> loader) {
        final ConfigHandler<Config> configHandler = new ConfigHandler<>(Config.class);
        configHandler.registerVersion(0, ConfigV0.class);
        configHandler.registerVersion(1, ConfigV1.class);
        configHandler.registerVersion(2, ConfigV2.class);
        configHandler.registerMigrationStep(0, old -> {
            final ConfigV0 configV0 = (ConfigV0) old;
            return new ConfigV1(1, new ConfigV1.Bar(configV0.foo().value()));
        });
        configHandler.registerMigrationStep(1, old -> {
            final ConfigV1 configV1 = (ConfigV1) old;
            return new ConfigV2(2, Component.text("Value: ", Style.style(NamedTextColor.RED,
                    TextDecoration.BOLD)).append(Component.text(configV1.bar().valueFromOldFoo(), NamedTextColor.AQUA)));
        });
        return configHandler.loadConfig(data, loader);
    }

    int version();
    Component testMessage();
}
