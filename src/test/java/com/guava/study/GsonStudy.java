package com.guava.study;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.junit.Test;

import com.base.Person;
import com.fasterxml.jackson.annotation.JsonAlias;
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

    @Test
    public void transMap2Bean() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("addr_ess", "beijing");
        map.put("name", "zhangsan");

        System.out.println(transMap2Bean(map, Person.class));
    }

    private <T> T transMap2Bean(Map<String, Object> map, Class<T> clazz) throws Exception {
        T t = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            JsonAlias alias = field.getAnnotation(JsonAlias.class);
            Object fieldVal = null;
            if (Objects.nonNull(alias) && alias.value().length > 0) {
                for (String aliasVal : alias.value()) {
                    fieldVal = map.get(aliasVal);
                    if (Objects.nonNull(fieldVal)) {
                        break;
                    }
                }
            } else {
                fieldVal = map.get(field.getName());
            }
            if (Objects.nonNull(fieldVal)) {
                BeanUtils.setProperty(t, field.getName(), fieldVal);
            }
        }
        return t;
    }
}
