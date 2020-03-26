package com.akka.study;

import java.io.Serializable;

/**
 * 招呼体,里面有打的什么招呼
 *
 * @author xuedui.zhao
 * @create 2019-06-16
 */
public class Greeting implements Serializable {

    public final String message;

    public Greeting(String message) {
        this.message = message;
    }
}
