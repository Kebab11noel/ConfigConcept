package com.noelnemeth.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ConfigHandler<R> {
    private final Class<R> configType;
    private int latestVersion = -1;
    private final Map<Integer, Class<?>> configClasses = new HashMap<>();
    private final Map<Integer, Function<Object, Object>> configMigrators = new HashMap<>();

    public ConfigHandler(Class<R> configType) {
        this.configType = configType;
    }

    public void registerVersion(int version, Class<?> clazz) {
        configClasses.put(version, clazz);
        latestVersion = Math.max(latestVersion, version);
    }

    public void registerMigrationStep(int fromVersion, Function<Object, Object> migrator) {
        configMigrators.put(fromVersion, migrator);
    }

    public <T> R loadConfig(T data, ConfigLoader<T> loader) {
        try {
            final int version = loader.load(data, Meta.class).version();
            final Class<?> sourceClass = configClasses.get(version);
            if (sourceClass == null) throw new IllegalArgumentException("Config version isn't supported!");
            Object conf = loader.load(data, sourceClass);
            for (int i = version; i < latestVersion; i++) {
                final Function<Object, Object> function = configMigrators.get(i);
                if (function == null) throw new RuntimeException("Migration path missing for %s -> %s".formatted(i, i+1));
                conf = function.apply(conf);
            }
            if (configType.isAssignableFrom(conf.getClass())) {
                return configType.cast(conf);
            } else {
                throw new RuntimeException("Latest config type mismatch!");
            }
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to load configuration!", throwable);
        }
    }

    public interface ConfigLoader<T> {
        <R> R load(T data, Class<R> clazz) throws Throwable;
    }
    private record Meta(int version) {}
}
