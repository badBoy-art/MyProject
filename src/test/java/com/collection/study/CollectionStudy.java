package com.collection.study;

import com.Extends.study.Animal;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author xuedui.zhao
 * @create 2018-11-23
 */
public class CollectionStudy {

    @Test
    public void test01() {
        List<Animal> animalList = Lists.newArrayList();
        Animal animal = Animal.builder()
                .age(17).name("zhangsan").build();
        Animal animal1 = Animal.builder()
                .age(18).name("lisi").build();
        animalList.add(animal);
        animalList.add(animal1);

        Collection<Animal> collection = Collections2.filter(animalList, new Predicate<Animal>() {
            @Override
            public boolean apply(@Nullable Animal input) {
                return input.getName().equals("wangwu");
            }
        });

        setAge(collection);

        System.out.println(collection);
    }

    private void setAge(Collection<Animal> collection) {
        for (Animal animal : collection) {
            if ("lisi".equals(animal.getName())) {
                animal.setAge(100);
            }
        }
    }


    private String getPassengerName2(final Animal animal, List<Animal> animalList) {
        for (Animal animal1 : animalList) {
            if (animal.getName().equals(animal1.getName())) {
                return "wangwu";
            }
        }
        return StringUtils.EMPTY;
    }

    @Test
    public void testArrayList() {
        final List<String> list = Lists.newArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        for (final String str : list) {
            System.out.println(str);
            if (str.equals("c")) {
                list.remove(str);
            }
            System.out.println(list);
        }

        for (final String str : list) {
            System.out.println(str);
            if (str.equals("b")) {
                list.remove(str);
            }
            System.out.println(list);
        }
    }

}
