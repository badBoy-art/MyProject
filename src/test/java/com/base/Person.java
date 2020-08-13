package com.base;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhaoxuedui <zhaoxuedui>
 * Created on 2020-04-01
 * @Description
 */
@Data
@Builder
public class Person {
    private String name;
    private Integer age;
    private String address;
    private List<String> hobbits;
}
