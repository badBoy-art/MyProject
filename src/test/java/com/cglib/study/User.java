package com.cglib.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author xuedui.zhao
 * @create 2019-06-14
 */
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class User {

    private String name;
    private String address;
    private Map hobbys;

}
