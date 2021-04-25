package com.Extends.study;

import org.python.modules._io._ioTest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-04-15
 * @Description
 */
@Accessors(chain = true)
@Data
public class Cat extends Animal {
    private String aa;
}
