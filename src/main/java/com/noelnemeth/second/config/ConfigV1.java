package com.noelnemeth.second.config;

record ConfigV1(int version, Bar bar) {
    public ConfigV1() {
        this(1, new Bar());
    }

    record Bar(int valueFromOldFoo) {
        public Bar() {
            this(15);
        }
    }
}
