package com.json.study.interfacedeser;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.json.study.jackson.JacksonSupport;
import com.json.study.jacksondeserializer.AnimalDeserializer;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-02
 * @Description
 */
public class TestDeser {

    private String str;

    @Before
    public void before() throws JsonProcessingException {
        People people = new People("zhangsan", "北京");
        Activite activite = new Activite();

        activite.setName("test");
        activite.setAnimal(people);

        ObjectMapper mapper = new ObjectMapper();
        str = (mapper.writeValueAsString(activite));
    }

    @Test
    public void test01() {
        Activite activite = JacksonSupport.parseJson(str, Activite.class);

        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//        activite = mapper.convertValue(str, Activite.class);
        System.out.println(activite);
    }

    @Test
    public void test02() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Activite.class, new AnimalDeserializer());
        mapper.registerModule(module);
        String json = "{\n" +
                "    \"name\":\"test\",\n" +
                "    \"animal\":{\n" +
                "        \"name\":\"zhnagsan\",\n" +
                "        \"address\":\"北京\"\n" +
                "    }\n" +
                "}";
        Activite activite = mapper.readValue(json, Activite.class);
        System.out.println(activite);
    }

}
