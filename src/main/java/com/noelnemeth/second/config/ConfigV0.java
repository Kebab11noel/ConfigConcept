package com.noelnemeth.second.config;

record ConfigV0(int version, Foo foo, Bar bar) {
    public ConfigV0() {
        this(0, new Foo(), new Bar());
    }

    record Foo(int value) {
        public Foo() {
            this(10);
        }
    }
    record Bar() {}

}
