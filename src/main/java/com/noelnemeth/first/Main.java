package com.noelnemeth.first;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.noelnemeth.first.config.Config;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());
        ymlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Config configInitial = Config.builder().value(5).build();
        System.out.printf("Initial value: %s\n", configInitial.foo().value());

        final String serialized = ymlMapper.writeValueAsString(configInitial);
        System.out.println(serialized);

        final Config configFinal = Config.load(serialized, ymlMapper::readValue);
        System.out.printf("Final value: %s", configFinal.foo().value());
    }
}
