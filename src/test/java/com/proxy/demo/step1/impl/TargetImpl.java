package com.proxy.demo.step1.impl;

import com.proxy.demo.step1.Target;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-07-02
 * @Description
 */
public class TargetImpl implements Target {

    @Override
    public void execute() {
        System.out.println("execute");
    }

}
