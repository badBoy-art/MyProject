package com.guice.study;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class UserServiceImpl implements UserService {

    @Override
    public void process() {
        System.out.println("我需要做一些业务逻辑");
    }
}
