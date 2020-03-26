package com.Extends.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 动物
 *
 * @author xuedui.zhao
 * @create 2018-05-09
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Animal {

    int age;
    String name;

    public void say(String voice) {
        System.out.print(voice);
    }
}
