package com.akka.study;

import java.io.Serializable;

/**
 * 打招呼的人
 *
 * @author xuedui.zhao
 * @create 2019-06-16
 */
public class WhoToGreet implements Serializable {

    public final String who;

    public WhoToGreet(String who) {
        this.who = who;
    }
}
