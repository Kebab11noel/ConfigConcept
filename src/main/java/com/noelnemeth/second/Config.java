package com.noelnemeth.second;

public interface Config {
    static Class<?> classOf(int version) {
        return switch (version) {
            case 0 -> ConfigV0.class;
            case 1 -> ConfigV1.class;
            default -> throw new IllegalArgumentException("Unsupported version!");
        };
    }

    static Config toLatest(int fromVersion, Object data) {
        return switch (fromVersion) {
            case 0 -> {
                final ConfigV0 old = (ConfigV0) data;
                yield toLatest(1, new ConfigV1(1, new ConfigV1.Bar(old.foo().value())));
            }
            case 1 -> (ConfigV1) data;
            default -> throw new IllegalArgumentException("Unsupported version!");
        };
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
