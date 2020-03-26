package com.jmx.study;

import javax.management.MXBean;

/**
 * Java管理扩展
 *
 * @author xuedui.zhao
 * @create 2018-12-17
 */
@MXBean
public interface JmxInterface {

    String say(String str);

}
