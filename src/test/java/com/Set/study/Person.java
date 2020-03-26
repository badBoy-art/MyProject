package com.Set.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xuedui.zhao
 * @create 2018-05-21
 */
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Comparable<Person>{

    @Setter
    @Getter
    private int age;

    @Setter
    @Getter
    private String name;

    @Override
    public int compareTo(Person o) {
        return (o.age > this.age ? 1 :
                (o.age == this.age ? 0 : -1));
    }
}
