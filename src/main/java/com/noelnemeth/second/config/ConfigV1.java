package com.noelnemeth.second.config;

record ConfigV1(int version, Bar bar) implements Config {
    public ConfigV1() {
        this(1, new Bar());
    }

    record Bar(int valueFromOldFoo) implements Config.Bar {
        public Bar() {
            this(15);
        }
    }
}
