package com.noelnemeth.first;

class ConfigBuilder {
    private int value;

    public ConfigBuilder value(int value) {
        this.value = value;
        return this;
    }

    public Config build() {
        return new ConfigV0(0, new ConfigV0.Foo(value), new ConfigV0.Bar());
    }
}
