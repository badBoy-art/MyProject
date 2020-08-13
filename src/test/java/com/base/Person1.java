package com.base;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-21
 * @Description
 */
@Data
@Builder
public class Person1 {

    private String name;
    private int age;
    private String address;
    private List<String> hobbits;
}
