package com.base;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;


/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-27
 * @Description
 */
public class Base {

    @Test
    public void tsetFilter() {
        List<String> strs = Lists.newArrayList("zhangsan", "lisi", "wangwu");
        System.out.println(strs.toString());
        System.out.println(strs.stream().filter(s -> !"lisi".equalsIgnoreCase(s)).collect(toList()));
    }

    @Test
    public void testListRemove() {
        List<Person> allPerson = Lists.newArrayList(Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("zs").build(),
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls").build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww").build());

        List<Person> persons = Lists.newArrayList(
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls").build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww").build());

        allPerson.removeAll(persons);
        System.out.println(allPerson);
        persons.addAll(allPerson);
        System.out.println(persons);

        List<Integer> brandIds = persons.stream()
                .mapToInt(Person::getAge).boxed().collect(Collectors.toList());
        System.out.println(brandIds);

        List<Person> empty = Lists.newArrayList();
        System.out.println(empty.stream()
                .mapToInt(Person::getAge).summaryStatistics().getSum());

    }

    @Test
    public void test() {
        /**
         * 它就是获取系统属性的
         */
        System.out.println(Long.getLong("12"));
        System.out.println(Long.getLong("idea.test.cyclic.buffer.size"));
        System.out.println(System.getProperty("idea.test.cyclic.buffer.size"));
        System.out.println(System.getProperties());
        System.out.println(Long.valueOf("12"));

    }
}
