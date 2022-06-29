package com.noelnemeth.second;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.noelnemeth.second.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final SimpleModule module = new SimpleModule();
        module.addSerializer(Component.class, new StdSerializer<>(Component.class) {
            @Override
            public void serialize(Component component, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(MiniMessage.miniMessage().serialize(component));
            }
        });
        module.addDeserializer(Component.class, new StdDeserializer<>(Component.class) {
            @Override
            public Component deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
                return MiniMessage.miniMessage().deserialize(jsonParser.getValueAsString());
            }
        });
        mapper.registerModule(module);

        //language=yaml
        final String serialized = """
                ---
                version: 0
                foo:
                  value: 5
                bar: {}
                """;
        System.out.println("Input:");
        System.out.println(serialized);

        final Config configFinal = Config.load(serialized, mapper::readValue);
        System.out.printf("Value: %s\n", configFinal.testMessage().toString());
        System.out.println("Latest version:");
        System.out.println(mapper.writeValueAsString(configFinal));
    }
}
