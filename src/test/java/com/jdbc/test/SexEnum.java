package com.jdbc.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * xingbie
 *
 * @author xuedui.zhao
 * @create 2019-04-23
 */
@AllArgsConstructor
@Getter
public enum SexEnum {

    man(1,"男人"),
    woman(0,"女人");

    int code;
    String desc;

}
