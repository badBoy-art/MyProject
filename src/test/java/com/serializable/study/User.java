package com.serializable.study;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author xuedui.zhao
 * @create 2019-05-20
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
@Accessors
public class User implements Serializable {

    private static final long serialVersionUID = -9157333081225106289L;
    private int id;
    private String name;

}
