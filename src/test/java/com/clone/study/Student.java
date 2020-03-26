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
public class Student implements Cloneable {
    private String name;
    private Class aClass;

    @Override
    protected Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
