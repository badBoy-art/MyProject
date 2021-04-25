package com.json.study.jacksondeserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.json.study.interfacedeser.Activite;
import com.json.study.interfacedeser.People;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-03
 * @Description
 */
public class AnimalDeserializer extends JsonDeserializer<Activite> {

    @Override
    public Activite deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String name = node.get("name").asText();
        JsonNode animal = node.get("animal");
        String pName = animal.get("name").asText();
        String address = animal.get("address").asText();
        return new Activite(name, new People(pName, address));
    }
}
