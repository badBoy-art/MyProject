package com.snow.spi.service.impl;

import com.snow.spi.service.DogService;

/**
 * 白狗
 *
 * @author xuedui.zhao
 * @create 2018-04-26
 */
public class BlackDogServiceImpl implements DogService{

    @Override
    public void sleep() {
        System.out.println("黑色dog。。。汪汪叫，不睡觉...");
    }
}
