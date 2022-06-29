package com.noelnemeth.second.config;

import net.kyori.adventure.text.Component;

public record ConfigV2(int version, Component testMessage) implements Config {
    public ConfigV2() {
        this(2, Component.text("Message :)"));
    }
}
