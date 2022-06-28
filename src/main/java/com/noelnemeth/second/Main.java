package com.noelnemeth.second;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.noelnemeth.second.config.Config;

public class Main {
    public static void main(String[] args) {
        final ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());
        ymlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //language=yaml
        final String serialized = """
                ---
                version: 0
                foo:
                  value: 5
                bar: {}
                """;
        System.out.println(serialized);

        final Config configFinal = Config.load(serialized, ymlMapper::readValue);
        System.out.printf("Final value: %s", configFinal.bar().valueFromOldFoo());
    }
}
