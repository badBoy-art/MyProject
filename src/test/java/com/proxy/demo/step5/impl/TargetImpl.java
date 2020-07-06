package com.proxy.demo.step5.impl;

import com.proxy.demo.step5.Target;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-02
 * @Description
 */
public class TargetImpl implements Target {

    @Override
    public void execute1() {
        System.out.println("execute1");
    }

    @Override
    public void execute2() {
        System.out.println("execute2");
    }
}
