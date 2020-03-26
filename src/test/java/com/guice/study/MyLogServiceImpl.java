package com.guice.study;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class MyLogServiceImpl implements LogService {

    @Override
    public void log(String msg) {
        System.out.println("------My_LOG:" + msg);
    }

}
