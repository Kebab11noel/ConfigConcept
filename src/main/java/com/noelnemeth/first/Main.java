package com.noelnemeth.first;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());
        ymlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Config configInitial = Config.builder().value(5).build();
        System.out.printf("Initial value: %s\n", configInitial.foo().value());

        final String serialized = ymlMapper.writeValueAsString(configInitial);
        System.out.println(serialized);

        record Meta(int version) {}

        final int version = ymlMapper.readValue(serialized, Meta.class).version;
        final Config configFinal = Config.toLatest(version, ymlMapper.readValue(serialized, Config.classOf(version)));
        System.out.printf("Final value: %s", configFinal.foo().value());
    }
}
