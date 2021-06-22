package com.guava.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;

import com.base.Person;
import com.google.gson.Gson;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-24
 * @Description
 */
public class GsonStudy {

    @Test
    public void test() {
        Person person = Person.builder()
                .name("zhangsan").age(11).build();

        String jstr = new Gson().toJson(person);
        System.out.println(jstr);
    }

    @Test
    public void testMapToJson() {
        Map<Long, List<Long>> map = new HashMap<>();
        map.put(1L, Lists.newArrayList(11L, 12L, 13L));
        map.put(2L, Lists.newArrayList(21L, 22L, 23L));

        Gson gson = new Gson();
        System.out.println(map);
        System.out.println(gson.toJson(map));

        Map<Long, List<Long>> map2 = new HashMap<Long, List<Long>>() {{
            put(1L, Lists.newArrayList(11L, 12L, 13L));
        }};
        System.out.println(map2);
        System.out.println(gson.toJson(map2, HashMap.class));
    }

}
