package com.json.study.interfacedeser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-02
 * @Description
 */
@JsonDeserialize(as = People.class)
public interface Animal {

    String say(String input);

}
