package com.snow.spi.service.impl;

import com.snow.spi.service.DogService;

/**
 * 黑狗
 *
 * @author xuedui.zhao
 * @create 2018-04-26
 */
public class WhiteDogServiceImpl implements DogService {

    @Override
    public void sleep() {
        System.out.println("白色dog。。。呼呼大睡觉...");
    }

}
