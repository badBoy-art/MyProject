package com.clone.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xuedui.zhao
 * @create 2019-07-16
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person implements Cloneable {
    private String name;
    private String sex;

    @Override
    protected Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}
