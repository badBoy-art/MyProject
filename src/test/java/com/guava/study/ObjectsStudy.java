package com.guava.study;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.Test;
import org.python.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * Guava-Object
 *
 * @author xuedui.zhao
 * @create 2018-03-13
 */
public class ObjectsStudy {

    @Test
    public void testObject() {
        boolean result = Objects.equals(null, "a");
        Assert.assertTrue(!result);
    }

    @Setter
    @Getter
    @ToString
    class Person implements Comparable<Person> {
        private String lastName;
        private String firstName;
        private int zipCode;

        @Override
        public int compareTo(Person that) {
            return ComparisonChain.start()
                    .compare(this.firstName, that.firstName)
                    .compare(this.lastName, that.lastName)
                    .compare(this.zipCode, that.zipCode, Ordering.natural().nullsLast())
                    .result();
        }
    }

    @Test
    public void testComparisonChain() {
        Person person = new Person();
        person.setFirstName("zhang");
        person.setLastName("san");
        person.setZipCode(1);

        Person person1 = new Person();
        person1.setFirstName("li");
        person1.setLastName("si");
        person1.setZipCode(2);

        Set<Person> set = Sets.newTreeSet();
        set.add(person);
        set.add(person1);
        System.out.println(set);
    }

    @Test
    public void testObjectName() {
        System.out.println(getClass().getSimpleName());
    }
}
