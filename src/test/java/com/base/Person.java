package com.base;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-01
 * @Description
 */
@Data
@Builder
public class Person {
    private String name;
    private int age;
    private String address;
}
