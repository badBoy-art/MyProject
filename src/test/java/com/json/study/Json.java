package com.json.study;

import static com.json.study.ObjectMapperUtils.fromJson;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.Extends.study.Dog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;

/**
 * @author xuedui.zhao
 * @create 2018-11-28
 */
public class Json {

    @Test
    public void test() {
        JsonObj jsonObj = new JsonObj();
        jsonObj.setAmount(new BigDecimal(10));
        System.out.println(JSON.toJSONString(jsonObj, SerializerFeature.WriteNullStringAsEmpty));
    }

    @Test
    public void test01() {

        String str = "{\"amount\":10,\"operator\":null}\n";
        JsonObj jsonObj = JSONObject.parseObject(str, JsonObj.class, Feature.InitStringFieldAsEmpty);
        System.out.println(jsonObj.toString());
    }

    @Test
    public void test02() {
        List<Dog> dogs = Lists.newArrayList();

        String json = JSON.toJSONString(dogs);

        System.out.println(json);
    }

    @Test
    public void test03() {


        String json = "[]";

        List<Dog> dogs = JSON.parseArray(json, Dog.class);

        System.out.println(dogs);
    }

    @Test
    public void testJsonPutSame() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("str", "12345");
        jsonObject.put("str", "67890");
        System.out.println(jsonObject);
    }

    @Test
    public void testTypeReference() {
        String str = "{\"create_time\":\"2019-07-18 20:33:12\",\"orderNo\":\"331966146644\",\"payDesire\":\" 0.614124\",\"price\":\"2536\",\"book_OK\":\"2019-07-18 20:28:10\" }";
        Map map = JSON.parseObject(str, new TypeReference<Map<String, String>>() {
        });
        System.out.println(map.get("orderNo"));
    }

    @Test
    public void testObjectMapperUtils() {
        String str = "{\"photoIds\":[\"52917608636\"]}";
        Map<String, Object> map = fromJson(str);
        map.forEach((key, value) -> {
            if (value instanceof Collection) {
                System.out.println("key = " + key + " value = " + ((List)value).get(0).getClass());
            }
        });
    }
}
