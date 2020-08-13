package com.base;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;


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
                        .name("zs")
                        .hobbits(Lists.newArrayList("1", "2"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls")
                        .hobbits(Lists.newArrayList("3", "4"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww")
                        .hobbits(Lists.newArrayList("5", "6"))
                        .build());

        List<Person> persons = Lists.newArrayList(
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls").build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww").build());

        List<String> hobbits = allPerson.stream()
                .flatMap(x -> x.getHobbits().stream())
                .collect(Collectors.toList());

        System.out.println(hobbits);

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
        System.out.println(Long.parseLong("12"));

    }

    @Test
    public void testClassName() {
        System.out.println(getClass().getName());
        System.out.println(getClass().getSimpleName());
    }

    @Test
    public void test0x7fff() {
        System.out.println(0x7fff);
    }

    @Test
    public void test_() {
        int a = 10;
        /**
         * a++ 分为三个阶段
         * 1、内存到寄存器
         * 2、寄存器自增
         * 3、写回内存
         * 这三个阶段中间都可以被中断分离开
         */
        int b = a++;
        int c = a + 1;
        System.out.println("a = " + a + " b = " + b + " c = " + c);
    }

    @Test
    public void testCollection() {
        List<Person> list = Lists.newArrayList(Person.builder()
                        .address("beijing")
                        .age(12)
                        .name("zs")
                        .hobbits(Lists.newArrayList("1", "2"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls")
                        .hobbits(Lists.newArrayList("3", "4"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww")
                        .hobbits(Lists.newArrayList("5", "6"))
                        .build());

        System.out.println(list.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList()));
    }

    @Test
    public void testGeneric() {
        OptionManager manager = new OptionManager();
    }

    @Test
    public void testSet() {
        int a = 3;

        int b = a = 4;

        System.out.println("a = " + a + ", b = " + b);
    }

    @Test
    public void test1() {
        System.out.println(1708374563 & 31);
        System.out.println(~4 + 1);
    }

    @Test
    public void test07() {
        System.out.println((1 << 5) - 1);
    }

    @Test
    public void testIntegerNullPointer() {
        Person person = Person.builder().name("zhangsan").build();
        Person1 person1 = Person1.builder().build();

        person1.setName(person.getName());
        person1.setAge(person.getAge());
    }

    @Test
    public void testHashCode() {
        System.out.println(Hashing.sha256().hashBytes("".getBytes()).toString());
        System.out.println(Hashing.sha256().hashBytes("cba".getBytes()).toString());
        System.out.println(Hashing.sha256().hashBytes("abc".getBytes()).toString());
    }


}
