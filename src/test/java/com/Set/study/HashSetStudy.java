package com.Set.study;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * hashSet学习
 *
 * @author xuedui.zhao
 * @create 2018-05-21
 */
public class HashSetStudy {

    @Test
    public void testHashSet() {
        Set<Person> hashSet = new HashSet<Person>();
        Person person = Person.builder().age(1).name("二鹏").build();
        Person person1 = Person.builder().age(2).name("大棚").build();
        hashSet.add(person);
        hashSet.add(person1);
        for (Person person2: hashSet) {
            System.out.println(person2.getName());
        }
        System.out.println("----------------");
        hashSet.remove(person);
        for (Person person2: hashSet) {
            System.out.println(person2.getName());
        }
    }

    @Test
    public void testHashSet01() {
        Set<Person1> hashSet = new HashSet<Person1>();
        Person1 person = Person1.builder().age(1).name("二鹏").build();
        Person1 person1 = Person1.builder().age(2).name("大棚").build();
        hashSet.add(person);
        hashSet.add(person1);
        for (Person1 person2: hashSet) {
            System.out.println(person2.getName());
        }
        System.out.println("----------------");
        person.setAge(20);
        hashSet.remove(person);
        //hashSet.add(person);
        for (Person1 person2: hashSet) {
            System.out.println(person2.getName());
        }
    }

    @Test
    public void test02() {
        int i = -2000000000;
        int j =  2000000000;

        System.out.println(i >>> 30);
        System.out.println(j >> 30);
        System.out.println(i >> 30);
    }

    @Test
    public void testToString() {
        Person person = Person.builder().age(1).name("二鹏").build();
        System.out.println(person.toString());
    }
}
