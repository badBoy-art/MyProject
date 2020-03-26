package com.Set.study;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xuedui.zhao
 * @create 2018-05-21
 */
@ToString
@Builder
public class Person1 implements Comparable<Person1>{

    @Setter
    @Getter
    private int age;

    @Setter
    @Getter
    private String name;

    @Override
    public int compareTo(Person1 o) {
        return (o.age > this.age ? 1 :
        (o.age == this.age ? 0 : -1));
    }

    @Override
    public int hashCode() {
        return age;
    }
}