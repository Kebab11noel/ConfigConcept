package com.noelnemeth.first.config;

record ConfigV0(int version, Foo foo, Bar bar) implements Config {
    @Override
    public int version() {
        return 0;
    }

    public ConfigV0() {
        this(0, new Foo(), new Bar());
    }

    record Foo(int value) implements Config.Foo {
        public Foo() {
            this(10);
        }
    }
    record Bar() implements Config.Bar {}

}
