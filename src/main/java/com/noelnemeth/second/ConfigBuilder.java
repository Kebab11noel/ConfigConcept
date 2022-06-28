package com.noelnemeth.second;

class ConfigBuilder {
    private int value;

    public ConfigBuilder value(int value) {
        this.value = value;
        return this;
    }

    public Config build() {
        return new ConfigV1(1, new ConfigV1.Bar(value));
    }
}
