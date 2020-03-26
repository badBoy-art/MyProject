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
public class Class implements Cloneable {
    private String name;
    private int classTime;

    @Override
    protected Class clone() throws CloneNotSupportedException {
        return (Class) super.clone();
    }
}
