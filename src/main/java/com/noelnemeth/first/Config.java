package com.noelnemeth.first;

public interface Config {
    static Class<?> classOf(int version) {
        return switch (version) {
            case 0 -> ConfigV0.class;
            default -> throw new IllegalArgumentException("Unsupported version!");
        };
    }

    static Config toLatest(int fromVersion, Object data) {
        return switch (fromVersion) {
            case 0 -> (ConfigV0) data;
            default -> throw new IllegalArgumentException("Unsupported version!");
        };
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
